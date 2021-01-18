package com.cyborgjenn.largecaves.entity;

import com.cyborgjenn.largecaves.util.Config;
import com.cyborgjenn.largecaves.util.ModLootTables;
import com.mojang.datafixers.DataFixer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class NMCRreeperEntity extends CreeperEntity {
	private static final DataParameter<Boolean> PERSIST = EntityDataManager.createKey(NMCRreeperEntity.class, DataSerializers.BOOLEAN);

	private int hitByArrowTime;
	private int timeSinceIgnited;
	private final int fuseTime = Config.NMFuseTime; //Maybe change to Random Int 20
	private final int explosionRadius = Config.NMBlastRadius;
	private int droppedSkulls;

	public NMCRreeperEntity(final EntityType<? extends CreeperEntity> type, final World world) {
		super(type, world);
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.NMCreeperHealth);
	}

	public static void registerFixesCreeper(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, NMCRreeperEntity.class);
	}

	protected void readAdditional() {
		super.entityInit();
		this.dataManager.register(STATE, -1);
		this.dataManager.register(POWERED, Boolean.FALSE);
		this.dataManager.register(IGNITED, Boolean.FALSE);
		this.dataManager.register(PERSIST, Boolean.FALSE);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (this.isEntityAlive()) {
			if (hitByArrowTime > 0) {
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
				--hitByArrowTime;
			} else {
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
			}
			int lastActiveTime = this.timeSinceIgnited;

			if (this.hasIgnited()) {
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();

			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;

			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
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
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);

		if (this.world.getGameRules().getBoolean("doMobLoot")) {
			if (cause.getTrueSource() instanceof EntitySkeleton) {
				int i = Item.getIdFromItem(Items.RECORD_13);
				int j = Item.getIdFromItem(Items.RECORD_WAIT);
				int k = i + this.rand.nextInt(j - i + 1);
				this.dropItem(Item.getItemById(k), 1);
			} else if (cause.getTrueSource() instanceof EntityCreeper && cause.getTrueSource() != this && ((EntityCreeper) cause.getTrueSource()).getPowered() && ((EntityCreeper) cause.getTrueSource()).ableToCauseSkullDrop()) {
				((EntityCreeper) cause.getTrueSource()).incrementDroppedSkulls();
				this.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0F);
			}
		}
	}

	@Nullable
	@Override
	protected ResourceLocation getLootTable() {
		return ModLootTables.LOOT_NIGHTMARE;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
			this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			player.swingArm(hand);

			if (!this.world.isRemote) {
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
	public boolean attackEntityFrom(DamageSource source, float amount) {
		Entity entity = source.getTrueSource();
		if (source instanceof EntityDamageSourceIndirect) {

			this.playSound(SoundEvents.ENTITY_CREEPER_HURT, 1, 1);
			hitByArrowTime = Config.NMCreeperBoostTime;
			if (Config.NMPotionEffect) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.getPotionById(15), 200));
			}
			return false;
		} else {
			//hitByArrowTime = 0;
			return super.attackEntityFrom(source, amount);

		}
	}

	private void explode() {
		if (!this.world.isRemote) {
			boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
			float f = this.getPowered() ? 2.0F : 1.0F;
			this.dead = true;
			this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float) this.explosionRadius * f, flag);
			float range = this.explosionRadius * 2 * f;
			AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
			List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			if (!list.isEmpty()) {
				for (int i1 = 0; i1 < list.size(); ++i1) {
					Entity entity = (Entity) list.get(i1);
					if (entity instanceof EntityLivingBase) {
						((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.getPotionById(20), 200));
						((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.getPotionById(2), 400));
						((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.getPotionById(15), 400));
						if (entity instanceof NMCRreeperEntity) {
							entity.getDataManager().set(POWERED, Boolean.valueOf(true));
						}
					}
				}
			}
			this.setDead();
			this.spawnLingeringCloud();
		}
	}

	private void spawnLingeringCloud() {
		Collection<PotionEffect> collection = this.getActivePotionEffects();

		if (!collection.isEmpty()) {
			EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
			entityareaeffectcloud.setRadius(2.5F);
			entityareaeffectcloud.setRadiusOnUse(-0.5F);
			entityareaeffectcloud.setWaitTime(10);
			entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
			entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float) entityareaeffectcloud.getDuration());

			for (PotionEffect potioneffect : collection) {
				entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
			}

			this.world.spawnEntity(entityareaeffectcloud);
		}
	}

	/**
	 * Returns true if an entity is able to drop its skull due to being blown up by this creeper.
	 * <p>
	 * Does not test if this creeper is charged; the caller must do that. However, does test the doMobLoot gamerule.
	 */
	@Override
	public boolean ableToCauseSkullDrop() {
		return this.droppedSkulls < 1 && this.world.getGameRules().getBoolean("doMobLoot");
	}

	@Override
	public void incrementDroppedSkulls() {
		++this.droppedSkulls;
	}

	@Override
	public boolean isNoDespawnRequired() {
		return false;
	}
}
