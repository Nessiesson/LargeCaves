package com.cyborgJenn.largeCaves.entity;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import com.cyborgJenn.largeCaves.util.Config;
import com.cyborgJenn.largeCaves.util.ModLootTables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityNMCreeper extends EntityCreeper{

	private static final DataParameter<Integer> STATE = EntityDataManager.<Integer>createKey(EntityNMCreeper.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> POWERED = EntityDataManager.<Boolean>createKey(EntityNMCreeper.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IGNITED = EntityDataManager.<Boolean>createKey(EntityNMCreeper.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> PERSIST = EntityDataManager.<Boolean>createKey(EntityNMCreeper.class, DataSerializers.BOOLEAN);
	
	private int lastActiveTime;
	private int hitByArrowTime;
	private int timeSinceIgnited;
	private int fuseTime = Config.NMFuseTime;//Maybe change to Random Int 20
	private int explosionRadius = Config.NMBlastRadius;
	private int droppedSkulls;


	public EntityNMCreeper(World par1World) {
		super(par1World);
		this.isImmuneToFire = true;
		this.setSize(0.8F, 1.8F);
	}
	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	protected boolean isValidLightLevel()
	{
		return true;
	}
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.NMCreeperHealth);
		//this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}
	/**
	 * The maximum height from where the entity is alowed to jump (used in pathfinder)
	 */
	public int getMaxFallHeight()
	{
		return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
	}

	public void fall(float distance, float damageMultiplier)
	{
		super.fall(distance, damageMultiplier);
		this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + distance * 1.5F);

		if (this.timeSinceIgnited > this.fuseTime - 5)
		{
			this.timeSinceIgnited = this.fuseTime - 5;
		}
	}
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(STATE, Integer.valueOf(-1));
		this.dataManager.register(POWERED, Boolean.valueOf(false));
		this.dataManager.register(IGNITED, Boolean.valueOf(false));
		this.dataManager.register(PERSIST, Boolean.valueOf(false));
	}
	public static void registerFixesCreeper(DataFixer fixer)
	{
		EntityLiving.registerFixesMob(fixer, EntityNMCreeper.class);
	}
	/**
	 * Called when a lightning bolt hits the entity.
	 */
	public void onStruckByLightning(EntityLightningBolt p_70077_1_)
	{
		super.onStruckByLightning(p_70077_1_);
		this.dataManager.set(POWERED, Boolean.valueOf(true));
	}
	/**
	 * Returns true if the creeper is powered by a lightning bolt.
	 */
	public boolean getPowered()
	{
		return ((Boolean)this.dataManager.get(POWERED)).booleanValue();
	}
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		if (((Boolean)this.dataManager.get(POWERED)).booleanValue())
		{
			compound.setBoolean("powered", true);
		}
		compound.setShort("Fuse", (short)this.fuseTime);
		compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
		compound.setBoolean("ignited", this.hasIgnited());
	}
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.dataManager.set(POWERED, Boolean.valueOf(compound.getBoolean("powered")));
		if (compound.hasKey("Fuse", 99))
		{
			this.fuseTime = compound.getShort("Fuse");
		}
		if (compound.hasKey("ExplosionRadius", 99))
		{
			this.explosionRadius = compound.getByte("ExplosionRadius");
		}
		if (compound.getBoolean("ignited"))
		{
			this.ignite();
		}
	}
	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		if (this.isEntityAlive())
		{
			if (hitByArrowTime > 0)
			{
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
				--hitByArrowTime;
			}
			else 
			{
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
			}
			this.lastActiveTime = this.timeSinceIgnited;

			if (this.hasIgnited())
			{
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();

			if (i > 0 && this.timeSinceIgnited == 0)
			{
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;

			if (this.timeSinceIgnited < 0)
			{
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime)
			{
				this.timeSinceIgnited = this.fuseTime;
				this.explode();
			}
		}

		super.onUpdate();
	}
	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);

		if (this.world.getGameRules().getBoolean("doMobLoot"))
		{
			if (cause.getTrueSource() instanceof EntitySkeleton)
			{
				int i = Item.getIdFromItem(Items.RECORD_13);
				int j = Item.getIdFromItem(Items.RECORD_WAIT);
				int k = i + this.rand.nextInt(j - i + 1);
				this.dropItem(Item.getItemById(k), 1);
			}
			else if (cause.getTrueSource() instanceof EntityCreeper && cause.getTrueSource() != this && ((EntityCreeper)cause.getTrueSource()).getPowered() && ((EntityCreeper)cause.getTrueSource()).ableToCauseSkullDrop())
			{
				((EntityCreeper)cause.getTrueSource()).incrementDroppedSkulls();
				this.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0F);
			}
		}
	}
	@Override
	public boolean attackEntityAsMob(Entity entityIn)
	{
		return true;
	}
	/**
	 * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public float getCreeperFlashIntensity(float p_70831_1_)
	{
		return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (float)(this.fuseTime - 2);
	}

	@Nullable
	@Override
	protected ResourceLocation getLootTable()
	{
		return ModLootTables.LOOT_NIGHTMARE;
	}
	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	@Override
	public int getCreeperState()
	{
		return ((Integer)this.dataManager.get(STATE)).intValue();
	}

	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(int state)
	{
		this.dataManager.set(STATE, Integer.valueOf(state));
	}
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);

		if (itemstack.getItem() == Items.FLINT_AND_STEEL)
		{
			this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			player.swingArm(hand);

			if (!this.world.isRemote)
			{
				this.ignite();
				itemstack.damageItem(1, player);
				return true;
			}
		}
		return super.processInteract(player, hand);
	}
	/**
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	Entity entity = source.getTrueSource();
		if (source instanceof EntityDamageSourceIndirect)
		{

			this.playSound(SoundEvents.ENTITY_CREEPER_HURT, 1, 1);
			hitByArrowTime = Config.NMCreeperBoostTime;
			if (Config.NMPotionEffect)
			{
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.getPotionById(15), 200));
			}
			return false;
		}
		else
		{
			//hitByArrowTime = 0;
			return super.attackEntityFrom(source, amount);

		}
    }
	private void explode()
	{
		if (!this.world.isRemote)
		{
			boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
			float f = this.getPowered() ? 2.0F : 1.0F;
			this.dead = true;
			this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius * f, flag);
			float range = this.explosionRadius * 2 * f;
			AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
			List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			if (!list.isEmpty())
			{
				for (int i1 = 0; i1 < list.size(); ++i1)
				{
					Entity entity = (Entity)list.get(i1);
					if (entity instanceof EntityLivingBase){
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.getPotionById(20), 200));
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.getPotionById(2), 400));
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.getPotionById(15), 400));
						if (entity instanceof EntityNMCreeper){
							entity.getDataManager().set(POWERED, Boolean.valueOf(true));
						}		
					}
				}
			}
			this.setDead();
			this.spawnLingeringCloud();
		}
	}

	private void spawnLingeringCloud()
	{
		Collection<PotionEffect> collection = this.getActivePotionEffects();

		if (!collection.isEmpty())
		{
			EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
			entityareaeffectcloud.setRadius(2.5F);
			entityareaeffectcloud.setRadiusOnUse(-0.5F);
			entityareaeffectcloud.setWaitTime(10);
			entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
			entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());

			for (PotionEffect potioneffect : collection)
			{
				entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
			}

			this.world.spawnEntity(entityareaeffectcloud);
		}
	}
	@Override
	public boolean hasIgnited()
	{
		return ((Boolean)this.dataManager.get(IGNITED)).booleanValue();
	}
	@Override
	public void ignite()
	{
		this.dataManager.set(IGNITED, Boolean.valueOf(true));
	}

	/**
	 * Returns true if an entity is able to drop its skull due to being blown up by this creeper.
	 *  
	 * Does not test if this creeper is charged; the caller must do that. However, does test the doMobLoot gamerule.
	 */
	@Override
	public boolean ableToCauseSkullDrop()
	{
		return this.droppedSkulls < 1 && this.world.getGameRules().getBoolean("doMobLoot");
	}
	@Override
	public void incrementDroppedSkulls()
	{
		++this.droppedSkulls;
	}
	public void setPersistance() {
		
		this.dataManager.set(PERSIST, Boolean.valueOf(true));
		
	}
	@Override
	public boolean isNoDespawnRequired(){
		
		return false;
		
	}
}
