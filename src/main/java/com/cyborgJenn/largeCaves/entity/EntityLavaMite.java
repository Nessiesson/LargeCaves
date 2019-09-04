package com.cyborgJenn.largeCaves.entity;

import java.util.Random;

import javax.annotation.Nullable;

import com.cyborgJenn.largeCaves.block.BlockLavaMite;
import com.cyborgJenn.largeCaves.block.ModBlocks;
import com.cyborgJenn.largeCaves.util.Config;
import com.cyborgJenn.largeCaves.util.ModLootTables;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityLavaMite extends EntityMob{

	private EntityLavaMite.AISummonLavaMite summonLavaMite;
	/**
	 * A cooldown before this entity will search for another Silverfish to join them in battle.
	 */
	private int allySummonCooldown;

	public EntityLavaMite(World par1World)
	{
		super(par1World);
		this.isImmuneToFire = true;
		this.setSize(0.5F, 0.3F);
		this.experienceValue = 10;
	}
	public static void registerFixesLavaMite(DataFixer fixer)
	{
		EntityLiving.registerFixesMob(fixer, EntityLavaMite.class);
	}

	protected void initEntityAI()
	{
		this.summonLavaMite = new EntityLavaMite.AISummonLavaMite(this);
		this.tasks.addTask(1, new EntityAISwimming(this));
		
		this.tasks.addTask(3, this.summonLavaMite);
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(5, new EntityLavaMite.AIHideInStone(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	public double getYOffset()
	{
		return 0.1D;
	}

	public float getEyeHeight()
	{
		return 0.3F;
	}
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3000000238418579D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
	}
	/**
     * Returns true if this entity is undead.
     */
    public boolean isEntityUndead()
    {
        return this.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD;
    }
    
	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	protected boolean canTriggerWalking()
	{
		return false;
	}

	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_SILVERFISH_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_SILVERFISH_HURT;
	}

	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_SILVERFISH_DEATH;
	}

	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15F, 1.0F);
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (this.isEntityInvulnerable(source))
		{
			return false;
		}
		else
		{
			if ((source instanceof EntityDamageSource || source == DamageSource.MAGIC) && this.summonLavaMite != null)
			{
				this.summonLavaMite.notifyHurt();
			}
			return super.attackEntityFrom(source, amount);
		}
	}
	@Nullable
	protected ResourceLocation getLootTable()
	{
		return ModLootTables.LOOT_LAVAMITE;
	}
	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		for (int i = 0; i < 2; ++i)
		{
			if (this.motionX > 0 || this.motionY > 0 || this.motionZ > 0){
				this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, null);
				this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, null);
			}

			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
		}
		this.renderYawOffset = this.rotationYaw;
		super.onUpdate();
	}
	protected void updateAITasks()
    {
        if (this.isWet())
        {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        } 
        super.updateAITasks();
    }
	/**
	 * Set the render yaw offset
	 */
	public void setRenderYawOffset(float offset)
	{
		this.rotationYaw = offset;
		super.setRenderYawOffset(offset);
	}
	/**
	 * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
	 * Args: x, y, z
	 */
	public float getBlockPathWeight(BlockPos pos)
	{
		return this.world.getBlockState(pos.down()).getBlock() == Blocks.NETHERRACK ? 10.0F : super.getBlockPathWeight(pos);
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	protected boolean isValidLightLevel()
	{
		return true;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	public boolean getCanSpawnHere()
	{
		if (super.getCanSpawnHere())
		{
			EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 5.0D);
			return entityplayer == null;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}
	static class AIHideInStone extends EntityAIWander
	{
		private EnumFacing facing;
		private boolean doMerge;

		public AIHideInStone(EntityLavaMite lavamiteIn)
		{
			super(lavamiteIn, 1.0D, 10);
			this.setMutexBits(1);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			if (this.entity.getAttackTarget() != null)
			{
				return false;
			}
			else if (!this.entity.getNavigator().noPath())
			{
				return false;
			}
			else if(!Config.shouldHideInBlock)
			{
				return false;
			}
			else
			{

				Random random = this.entity.getRNG();

				if (this.entity.world.getGameRules().getBoolean("mobGriefing") && random.nextInt(10) == 0)
				{
					this.facing = EnumFacing.random(random);
					BlockPos blockpos = (new BlockPos(this.entity.posX, this.entity.posY + 0.5D, this.entity.posZ)).offset(this.facing);
					IBlockState iblockstate = this.entity.world.getBlockState(blockpos);
					if (BlockLavaMite.canContainLavaMite(iblockstate))
					{
						this.doMerge = true;
						return true;
					}
				}
				this.doMerge = false;
				return super.shouldExecute();
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting()
		{
			return this.doMerge ? false : super.shouldContinueExecuting();
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting()
		{
			if (!this.doMerge)
			{
				super.startExecuting();
			}
			else
			{
				World world = this.entity.world;
				BlockPos blockpos = (new BlockPos(this.entity.posX, this.entity.posY + 0.5D, this.entity.posZ)).offset(this.facing);
				IBlockState iblockstate = world.getBlockState(blockpos);
				if (BlockLavaMite.canContainLavaMite(iblockstate))
				{
					world.setBlockState(blockpos, ModBlocks.LAVAMITE.getDefaultState(), 3);
					this.entity.spawnExplosionParticle();
					this.entity.setDead();
				}
			}
		}
	}

	static class AISummonLavaMite extends EntityAIBase
	{
		private final EntityLavaMite lavamite;
		private int lookForFriends;

		public AISummonLavaMite(EntityLavaMite lavamiteIn)
		{
			this.lavamite = lavamiteIn;
		}

		public void notifyHurt()
		{
			if (this.lookForFriends == 0)
			{
				this.lookForFriends = 20;
			}
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return this.lookForFriends > 0;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask()
		{
			--this.lookForFriends;

			if (this.lookForFriends <= 0)
			{
				World world = this.lavamite.world;
				Random random = this.lavamite.getRNG();
				BlockPos blockpos = new BlockPos(this.lavamite);

				for (int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i)
				{
					for (int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j)
					{
						for (int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k)
						{
							BlockPos blockpos1 = blockpos.add(j, i, k);
							IBlockState iblockstate = world.getBlockState(blockpos1);

							if (iblockstate.getBlock() == ModBlocks.LAVAMITE)
							{
								if (world.getGameRules().getBoolean("mobGriefing") || !Config.doRespectMobGriefing)
								{
									world.destroyBlock(blockpos1, true);
								}
								else
								{
									world.setBlockState(blockpos1, ModBlocks.LAVAMITE.getDefaultState(), 3);
								}

								if (random.nextBoolean())
								{
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}
