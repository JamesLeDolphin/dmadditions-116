package com.jdolphin.dmadditions.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.ChatUtil.MessageType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class HerobrineEntity extends MonsterEntity {

	protected Inventory inventory = new Inventory(36);
	private int targetChangeTime;

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes()
				.add(Attributes.ATTACK_DAMAGE, 1.0D)
				.add(Attributes.MOVEMENT_SPEED, (double) 0.2F)
				.add(Attributes.ATTACK_SPEED)
				.add(Attributes.LUCK)
				.add(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get())
				.add(Attributes.ATTACK_KNOCKBACK)
				.add(Attributes.FOLLOW_RANGE, 20.0D);
	}

	public HerobrineEntity(EntityType<? extends MonsterEntity> type, World world) {
		super(type, world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new TeleportOutOfWaterGoal(this));
		this.goalSelector.addGoal(1, new TeleportWhenCloseGoal<PlayerEntity>(this, PlayerEntity.class, false, 28));
		this.goalSelector.addGoal(5, new RandomWalkingGoal((CreatureEntity) this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 32.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(8, new RandomScaryNoiseGoal(this, 200, new SoundEvent[]{
			SoundEvents.ANVIL_FALL,
			SoundEvents.ENDERMAN_SCREAM,
			SoundEvents.AMBIENT_CAVE,
		}));

		this.goalSelector.addGoal(11, new PlaceBlockGoal(this, 20));
		this.goalSelector.addGoal(11, new TakeBlockGoal(this, 10));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if(source.isCreativePlayer() 
		|| source.isBypassInvul()
		|| source.getEntity() instanceof JimEntity) {
			super.hurt(source, amount);
			return true;
		}

		teleport();
		return false;
	}

	protected boolean teleport() {
		if (!this.level.isClientSide() && this.isAlive()) {
			double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
			double d1 = this.getY() + (double) (this.random.nextInt(64) - 32);
			double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
			return this.teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	public boolean switchMainHandItem(int slot){
		ItemStack mainHandItem = this.getMainHandItem();

		ItemStack item = inventory.getItem(slot);

		this.setItemInHand(Hand.MAIN_HAND, item);
		this.inventory.setItem(slot, mainHandItem);

		return true;
	}

	public BlockState getCarriedBlock(){
		ItemStack stack = this.getMainHandItem();
		Item item = stack.getItem();
		if(!(item instanceof BlockItem)) return null;

		return ((BlockItem) item).getBlock().defaultBlockState();
	}

	private boolean teleport(double x, double y, double z) {
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(x, y, z);

		while (blockpos$mutable.getY() > 0
				&& !this.level.getBlockState(blockpos$mutable).getMaterial().blocksMotion()) {
			blockpos$mutable.move(Direction.DOWN);
		}

		BlockState blockstate = this.level.getBlockState(blockpos$mutable);
		boolean flag = blockstate.getMaterial().blocksMotion();
		boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			net.minecraftforge.event.entity.living.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory
					.onEnderTeleport(this, x, y, z);
			if (event.isCanceled())
				return false;
			boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
			if (flag2 && !this.isSilent()) {
				this.level.playSound((PlayerEntity) null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT,
						this.getSoundSource(), 1.0F, 1.0F);
				this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
			}

			return flag2;
		} else {
			return false;
		}
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public boolean isInventoryFull() {
		for(int i = 0; i <= inventory.getContainerSize(); i++)
			if(inventory.getItem(i).isEmpty()) return false;

		return true;
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		for (int i = 0; i <= this.inventory.getContainerSize(); i++) {
			this.spawnAtLocation(this.inventory.getItem(i));
		}
	}

	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.put("Inventory", this.inventory.createTag());
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		this.inventory.fromTag(nbt.getList("Inventory", 10));
	}

	public boolean inventoryContainsBlocks(){
		for(int i = 0; i <= inventory.getContainerSize(); i++){
			if(inventory.getItem(i).getItem() instanceof BlockItem)
				return true;
		}

		return false;
	}

	public List<Integer> getInventorySlotsWithBlocks(){
		ArrayList<Integer> list = new ArrayList<>();

		for(int i = 0; i <= inventory.getContainerSize(); i++){
			if(inventory.getItem(i).getItem() instanceof BlockItem)
				list.add(i);
		}

		return list;
	}

	@Override
	protected void pickUpItem(ItemEntity item) {
		super.pickUpItem(item);

		if(item.removed) return;

		ItemStack stack = item.getItem();

		if(inventory.canAddItem(stack)){
			inventory.addItem(stack);
			this.take(item, stack.getCount());
			item.remove();
		}
	}

	@Override
	public boolean canPickUpLoot() {
		return true;
	}

	@Override
	public void setTarget(@Nullable LivingEntity target) {
		if (target == null) {
			this.targetChangeTime = 0;
		} else {
			this.targetChangeTime = this.tickCount;
		}

		super.setTarget(target); 
	}

	protected void customServerAiStep() {
		if (this.level.isDay() && this.tickCount >= this.targetChangeTime + 600) {
			float f = this.getBrightness();
			if (f > 0.5F && this.level.canSeeSky(this.blockPosition()) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity)null);
				this.teleport();
				this.targetChangeTime = this.tickCount;
			}
		}

		super.customServerAiStep();
	}

	static class PlaceBlockGoal extends Goal {
		private final HerobrineEntity herobrine;
		protected final int interval;

		public PlaceBlockGoal(HerobrineEntity entity) {
			this(entity, 2000);
		}

		public PlaceBlockGoal(HerobrineEntity entity, int interval) {
			this.herobrine = entity;
			this.interval = interval;
		}

		public boolean canUse() {
			if(!herobrine.inventoryContainsBlocks() && this.herobrine.getCarriedBlock() == null)
				return false;

			if(!(herobrine.getMainHandItem().getItem() instanceof BlockItem)) {
				List<Integer> slots = herobrine.getInventorySlotsWithBlocks();

				herobrine.switchMainHandItem(
					slots.get(herobrine.random.nextInt(slots.size()))
				);

			}

			if (this.herobrine.getCarriedBlock() == null) 
				return false;

			if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.herobrine.level, this.herobrine)) 
				return false;

			return this.herobrine.getRandom().nextInt(20) == 0;
		}

		public void tick() {
			Random random = this.herobrine.getRandom();
			World world = this.herobrine.level;
			int i = MathHelper.floor(this.herobrine.getX() - 1.0D + random.nextDouble() * 2.0D);
			int j = MathHelper.floor(this.herobrine.getY() + random.nextDouble() * 2.0D);
			int k = MathHelper.floor(this.herobrine.getZ() - 1.0D + random.nextDouble() * 2.0D);
			BlockPos blockpos = new BlockPos(i, j, k);
			BlockState blockstate = world.getBlockState(blockpos);
			BlockPos blockpos1 = blockpos.below();
			BlockState blockstate1 = world.getBlockState(blockpos1);
			BlockState blockstate2 = this.herobrine.getCarriedBlock();
			if (blockstate2 != null) {
				blockstate2 = Block.updateFromNeighbourShapes(blockstate2, this.herobrine.level, blockpos);
				if (this.canPlaceBlock(world, blockpos, blockstate2, blockstate, blockstate1, blockpos1) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(herobrine, net.minecraftforge.common.util.BlockSnapshot.create(world.dimension(), world, blockpos1), net.minecraft.util.Direction.UP)) {

					herobrine.lookAt(EntityAnchorArgument.Type.EYES, Helper.blockPosToVec3(blockpos));
					herobrine.swing(Hand.MAIN_HAND);

					world.setBlock(blockpos, blockstate2, 3);
					this.herobrine.getItemInHand(Hand.MAIN_HAND).shrink(1);

				}

			}
		}

		private boolean canPlaceBlock(World p_220836_1_, BlockPos p_220836_2_, BlockState p_220836_3_, BlockState p_220836_4_, BlockState p_220836_5_, BlockPos p_220836_6_) {
			return p_220836_4_.isAir(p_220836_1_, p_220836_2_) && !p_220836_5_.isAir(p_220836_1_, p_220836_6_) && !p_220836_5_.is(Blocks.BEDROCK) && !p_220836_5_.is(net.minecraftforge.common.Tags.Blocks.ENDERMAN_PLACE_ON_BLACKLIST) && p_220836_5_.isCollisionShapeFullBlock(p_220836_1_, p_220836_6_) && p_220836_3_.canSurvive(p_220836_1_, p_220836_2_) && p_220836_1_.getEntities(this.herobrine, AxisAlignedBB.unitCubeFromLowerCorner(Vector3d.atLowerCornerOf(p_220836_2_))).isEmpty();
		}
	}

	static class TakeBlockGoal extends Goal {
		private final HerobrineEntity herobrine;
		protected final int interval;
		protected BlockState blockstate;
		protected BlockPos blockpos;
		protected ItemStack itemStack;

		public TakeBlockGoal(HerobrineEntity entity, int interval) {
			this.herobrine = entity;
			this.interval = interval;
		}

		public TakeBlockGoal(HerobrineEntity entity) {
			this(entity, 20);
		}

		public boolean canUse() {
			if (!ForgeEventFactory.getMobGriefingEvent(this.herobrine.level, this.herobrine)) 
				return false;

			if(!findBlock())
				return false;

			if(!herobrine.inventory.canAddItem(itemStack))
				return false;
			
			return this.herobrine.getRandom().nextInt(interval) == 0;
		}

		protected boolean findBlock(){
			Random random = this.herobrine.getRandom();
			World world = this.herobrine.level;
			int i = MathHelper.floor(this.herobrine.getX() - 2.0D + random.nextDouble() * 4.0D);
			int j = MathHelper.floor(this.herobrine.getY() + random.nextDouble() * 3.0D);
			int k = MathHelper.floor(this.herobrine.getZ() - 2.0D + random.nextDouble() * 4.0D);
			blockpos = new BlockPos(i, j, k);
			blockstate = world.getBlockState(blockpos);
			Vector3d vector3d = new Vector3d((double) MathHelper.floor(this.herobrine.getX()) + 0.5D, (double) j + 0.5D,
					(double) MathHelper.floor(this.herobrine.getZ()) + 0.5D);
			Vector3d vector3d1 = new Vector3d((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D);
			BlockRayTraceResult blockraytraceresult = world.clip(new RayTraceContext(vector3d, vector3d1,
					RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, this.herobrine));

			itemStack = new ItemStack(blockstate.getBlock().asItem());
			return blockraytraceresult.getBlockPos().equals(blockpos);
		}

		public void tick() {
			boolean flag = findBlock();
			Block block = blockstate.getBlock();

			World world = this.herobrine.level;

			if (!block.asItem().equals(Items.AIR) && flag) {
				herobrine.lookAt(EntityAnchorArgument.Type.EYES, Helper.blockPosToVec3(blockpos));
				herobrine.swing(Hand.MAIN_HAND);

				world.removeBlock(blockpos, false);
				this.herobrine.getInventory().addItem(itemStack);
			}

		}
	}

	static class TeleportOutOfWaterGoal extends SwimGoal{
		protected HerobrineEntity herobrineEntity;

		public TeleportOutOfWaterGoal(HerobrineEntity entity) {
			super(entity);
			this.herobrineEntity = entity;
		}

		@Override
		public void tick() {
			herobrineEntity.teleport();
		}

	}

	static class TeleportWhenCloseGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T>{
		protected HerobrineEntity herobrineEntity;
		protected double range;

		public TeleportWhenCloseGoal(HerobrineEntity mob, Class<T> clazz, boolean mustSee, double range) {
			super(mob, clazz, mustSee);

			herobrineEntity = mob;
			this.range = range;
		}

		@Override
		public void tick() {
			super.tick();

			if(target.distanceTo(herobrineEntity) <= range) {
				herobrineEntity.teleport();
			}
		}
	}

	static class RandomScaryNoiseGoal extends Goal{
		protected int interval;
		MobEntity mob;
		SoundEvent[] sounds;

		public RandomScaryNoiseGoal(MobEntity mobEntity, int interval, SoundEvent[] sounds){
			this.interval = interval;
			this.mob = mobEntity;
			this.sounds = sounds;
		}

		@Override
		public boolean canUse() {
            if (this.mob.getRandom().nextInt(this.interval) != 0) {
               return false;
            }

			return true;
		}

		@Override
		public void start() {
			super.start();

			SoundEvent sound = sounds[mob.getRandom().nextInt(sounds.length)];

			this.mob.playSound(sound, 0.5f, 1f);
		}

	}

	public boolean checkSpawnRules(IWorld world, SpawnReason spawnReason) {
		if(spawnReason == SpawnReason.NATURAL){
			List<HerobrineEntity> others = world.getEntitiesOfClass(HerobrineEntity.class, this.getBoundingBox().inflate(256));

			if(others.size() > 0){
				return false;
			}

			if(this.random.nextInt(4) == 4){
				world.players().forEach(player -> {
					ChatUtil.sendMessageToPlayer(player, 
						new TranslationTextComponent("multiplayer.player.joined", "Herobrine").withStyle(TextFormatting.YELLOW), 
						MessageType.CHAT);
				});
			}
		}

		return true;
	}
}
