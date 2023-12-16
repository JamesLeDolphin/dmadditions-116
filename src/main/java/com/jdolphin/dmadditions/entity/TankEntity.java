package com.jdolphin.dmadditions.entity;

import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMProjectiles.Laser;
import com.swdteam.common.init.DMSoundEvents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TankEntity extends MobEntity implements IJumpingMount{

	private TankPartEntity bodyFront;
	private TankPartEntity bodyBack;
	private TankPartEntity turret;
	private TankPartEntity turretBarrel;
	private TankPartEntity[] subEntities;
	public final double[][] positions = new double[64][3];
	public int posPointer = -1;
	public float turretRot;
	protected Laser laserType = DMProjectiles.EXPLOSIVE_LASER; //TODO make this changeable

	@Deprecated
	public Optional<TankPartEntity> getPart(String name){
		return Stream.of(subEntities).filter(part -> part.name.equals(name)).findFirst();
	}

	public TankEntity(EntityType<? extends MobEntity> p_i48576_1_, World p_i48576_2_) {
		super(p_i48576_1_, p_i48576_2_);

		this.bodyFront = new TankPartEntity(this, "bodyFront", 7f, 2f);
		this.bodyBack = new TankPartEntity(this, "bodyBack", 7f, 2.5f);
		this.turret = new TankPartEntity(this, "turret", 6f, 2f);
		this.turretBarrel = new TankPartEntity(this, "turretBarrel", 6f, 0.5f);
		this.subEntities = new TankPartEntity[]{ bodyFront, bodyBack, turret, turretBarrel };

		this.setId(ENTITY_COUNTER.getAndAdd(this.subEntities.length + 1) + 1); 

		this.lookControl = new LookController(this){
			@Override
			public void tick() { }
		};

		this.turretRot = this.yHeadRot;
	}

	@Override
	public void tick() {
		super.tick();
	}

	protected float rotlerp(float p_75639_1_, float p_75639_2_, float p_75639_3_) {
		float f = MathHelper.wrapDegrees(p_75639_2_ - p_75639_1_);
		if (f > p_75639_3_) {
			f = p_75639_3_;
		}

		if (f < -p_75639_3_) {
			f = -p_75639_3_;
		}

		float f1 = p_75639_1_ + f;
		if (f1 < 0.0F) {
			f1 += 360.0F;
		} else if (f1 > 360.0F) {
			f1 -= 360.0F;
		}

		return f1;
	}

	@Override
	public void setId(int p_145769_1_) {
		super.setId(p_145769_1_);

		for(int i = 0; i < subEntities.length; ++i) 
			subEntities[i].setId(p_145769_1_ + i + 1);
	}

	@Override
	public boolean canBeControlledByRider() {
		return this.getControllingPassenger() instanceof LivingEntity;
	}

	@Nullable
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	public boolean canBeSteered() {
		return this.getControllingPassenger() instanceof LivingEntity;
	}

	@Override
	public void travel(Vector3d vec) {
		if(!this.isAlive()) return;
		

		if(this.isVehicle() && this.canBeSteered()){
			LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
//  			this.yRot = livingentity.yRot;
 			this.yRotO = this.yRot;
			this.xRot = livingentity.xRot * 0.5F;
 			this.setRot(this.yRot, this.xRot);
 			this.yBodyRot = this.yRot;
			this.yHeadRot = this.yBodyRot;

			float f1 = livingentity.zza;

			if (f1 <= 0.0F) {
				f1 *= 0.25F;
			}

			if (f1 > 0.5F) {
				f1 = 0.5F;
			}

			float f2 = MathHelper.sin(this.yRot * 0.017453292F);
			float f3 = MathHelper.cos(this.yRot * 0.017453292F);

			if(f1 > 0.0F) {
				this.setDeltaMovement(this.getDeltaMovement().add(-0.2F * f2, 0, 0.2F * f3));
			}
			if(f1 < 0.0f){
				this.setDeltaMovement(this.getDeltaMovement().add(0.2F * f2, 0, -0.2F * f3));
				if(livingentity.xxa != 0) this.yRot += livingentity.xxa * 2;
			}else{
				if(livingentity.xxa != 0) this.yRot -= livingentity.xxa * 2;
			}

 			this.turretRot = (float) Math.toRadians(livingentity.yHeadRot);

			// this.setDeltaMovement(this.getDeltaMovement().add(0, 0, f1));
		}

		super.travel(vec);
	}

	@Override
	public boolean isMultipartEntity() {
		return true;
	}

	@Override
	public net.minecraftforge.entity.PartEntity<?>[] getParts() {
		return subEntities;
	}

	@Override
	protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		if(player.getItemInHand(hand).isEmpty()){
			player.startRiding(this);

			if(player.level.isClientSide)
				setThirdPerson();
		}
		return super.mobInteract(player, hand);
	}

	@OnlyIn(Dist.CLIENT)
	protected void setThirdPerson(){
		Minecraft instance = Minecraft.getInstance();
		instance.options.setCameraType(PointOfView.THIRD_PERSON_BACK);
	}

	@Override
	public void positionRider(Entity entity) { 
		if (this.hasPassenger(entity)) {
			float forwardOffset = 1f;
			float leftOffset = 0.5f;
			float verticalOffset = -1f;

			float f1 = (float) ((!this.isAlive() ? 0.01F : this.getPassengersRidingOffset()) + entity.getMyRidingOffset() + verticalOffset);
			Vector3d vector3d = (new Vector3d(forwardOffset, 0.0D, leftOffset)).yRot(-this.turretRot);
			entity.setPos(this.turret.getX() + vector3d.x, this.turret.getY() + (double) f1, this.turret.getZ() + vector3d.z);
		}
	}

	@Override
	public Vector3d getDismountLocationForPassenger(LivingEntity p_230268_1_) {
		return new Vector3d(this.getX(), this.turret.getBoundingBox().maxY, this.getZ());
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void onPlayerJump(int p_110206_1_) {
	}

	@Override
	public boolean canJump() {
		return true; //TODO add cooldown or something
	}

	@Override
	public void handleStartJump(int p_184775_1_) { //possible TODO: use mouse buttons or something instead of jumping lol
		if(level instanceof ServerWorld){
			level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), DMSoundEvents.ITEM_GUN_SHOOT.get(), SoundCategory.NEUTRAL, 1.0F, 1.0F);

			LaserEntity laser = new LaserEntity(this.level, this, 0.0F, (float) this.getAttributes().getValue(Attributes.ATTACK_DAMAGE));
			laser.setLaserType(this.laserType);
			laser.setEmitsSmoke(true);
			laser.setDamageSource(new EntityDamageSource("dalekgun", this.getControllingPassenger()));
			laser.moveTo(turret.getPosition(1).add(0, 1, 0)); //FIXME positioning or something
			laser.shoot(turret, this.turret.xRot /* TODO add x rotation */, (float) Math.toDegrees(turretRot), 0.0F, 2.5F, 0.0F);
			this.level.addFreshEntity(laser);
		}
	}

	@Override
	public void handleStopJump() {
	} 

	protected boolean hurt(TankPartEntity part, DamageSource source, float amount) {
		return this.hurt(source, amount);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		
		Vector3d[] avector3d = new Vector3d[this.subEntities.length];
		for(int j = 0; j < this.subEntities.length; ++j) {
			avector3d[j] = new Vector3d(this.subEntities[j].getX(), this.subEntities[j].getY(), this.subEntities[j].getZ());
		}

		float tankYaw = this.yRot * ((float) Math.PI / 180F);
		float tankPitch = this.xRot * ((float) Math.PI / 180F);

		float turretYaw = this.turretRot;

		// Movement factors
		float cosYaw = MathHelper.cos(tankYaw);
		float sinYaw = MathHelper.sin(tankYaw);
		float turretCosYaw = MathHelper.cos(turretYaw);
		float turretSinYaw = MathHelper.sin(turretYaw);
		float cosPitch = MathHelper.cos(tankPitch);
		float sinPitch = MathHelper.sin(tankPitch);

		float f15 = (float)(this.getLatencyPos(5, 1.0F)[1] - this.getLatencyPos(10, 1.0F)[1]) * 10.0F * ((float)Math.PI / 180F);

		movePart(this.turretBarrel, -turretSinYaw, 3.5, turretCosYaw, 4.5f);
		movePart(this.turret, -sinYaw, 2, cosYaw, -1.5f);
		movePart(this.bodyFront, -sinYaw, 0, cosYaw, 1f);
		movePart(this.bodyBack, sinYaw, 0, -cosYaw, 4f);

		for(int l = 0; l < this.subEntities.length; ++l) {
			this.subEntities[l].xo = avector3d[l].x;
			this.subEntities[l].yo = avector3d[l].y;
			this.subEntities[l].zo = avector3d[l].z;
			this.subEntities[l].xOld = avector3d[l].x;
			this.subEntities[l].yOld = avector3d[l].y;
			this.subEntities[l].zOld = avector3d[l].z;
		}
	}

	private void movePart(TankPartEntity tankPart, double xOffset, double yOffset, double zOffset, float multiplier) {
		double newX = xOffset * multiplier;
		double newY = yOffset;
		double newZ = zOffset * multiplier;

		tickPart(tankPart, newX, newY, newZ);
	}

	public double[] getLatencyPos(int stepsBack, float progress) {
		if (this.isDeadOrDying()) {
			progress = 0.0F;
		}

		progress = 1.0F - progress;
		int currentIndex = this.posPointer - stepsBack & 63;
		int prevIndex = this.posPointer - stepsBack - 1 & 63;

		double[] result = new double[3];

		result[0] = MathHelper.wrapDegrees(MathHelper.lerp(progress, this.positions[currentIndex][0], this.positions[prevIndex][0]));
		result[1] = MathHelper.lerp(progress, this.positions[currentIndex][1], this.positions[prevIndex][1]);
		result[2] = MathHelper.lerp(progress, this.positions[currentIndex][2], this.positions[prevIndex][2]);
		return result;
	}

	private void tickPart(TankPartEntity part, double x, double y, double z) {
		part.setPos(this.getX() + x, this.getY() + y, this.getZ() + z);
	}

	static class TankPartEntity extends net.minecraftforge.entity.PartEntity<TankEntity> {
		public final TankEntity parentMob;
		public final String name;
		private final EntitySize size;


		public TankPartEntity(TankEntity parent, String name, float width, float height) {
			super(parent);
			this.size = EntitySize.scalable(width, height);
			this.refreshDimensions();
			this.parentMob = parent;
			this.name = name;

			this.setNoGravity(true);
		}

		@Override
		public ActionResultType interact(PlayerEntity player, Hand hand) {
			return parentMob.mobInteract(player, hand);
		}

		@Override
		public boolean canBeCollidedWith() {
			return true;
		}

		protected void defineSynchedData() {
		}

		protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {
		}

		protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {
		}

		public boolean isPickable() {
			return true;
		}

		public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
			return this.isInvulnerableTo(p_70097_1_) ? false : this.parentMob.hurt(this, p_70097_1_, p_70097_2_);
		}

		public boolean is(Entity p_70028_1_) {
			return this == p_70028_1_ || this.parentMob == p_70028_1_;
		}

		public IPacket<?> getAddEntityPacket() {
			throw new UnsupportedOperationException();
		}

		public EntitySize getDimensions(Pose p_213305_1_) {
			return this.size;
		}
	}

}
