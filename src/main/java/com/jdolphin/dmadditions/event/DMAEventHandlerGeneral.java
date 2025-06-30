package com.jdolphin.dmadditions.event;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.cap.IPlayerRegenCap;
import com.jdolphin.dmadditions.cap.PlayerRegenCapability;
import com.jdolphin.dmadditions.commands.HandlesCommands;
import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.jdolphin.dmadditions.entity.DalekMutantEntity;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.util.DMAData;
import com.jdolphin.dmadditions.util.Helper;
import com.jdolphin.dmadditions.world.dimension.Gravity;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.event.custom.tardis.TardisEvent;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.world.Schematic;
import com.swdteam.util.world.SchematicUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.tardis.mod.blocks.TransductionBarrierBlock;
import net.tardis.mod.helper.WorldHelper;
import net.tardis.mod.tileentities.IAffectTARDISLanding;
import net.tardis.mod.tileentities.machines.TransductionBarrierTile;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DMAEventHandlerGeneral {
	public static final ResourceLocation PLAYER_DATA_CAP = Helper.createAdditionsRL("player_data");

	@SubscribeEvent
	public static void onPlayerChat(ServerChatEvent event) {
		PlayerEntity player = event.getPlayer();
		String message = event.getMessage();
		ItemStack stack = player.getItemInHand(player.getUsedItemHand());
		if (stack.getItem().equals(DMAItems.HANDLES.get())) {
			if (message.toLowerCase().startsWith("handles")) {
				String query = message.substring(7).trim();

				if (query.isEmpty()) {
					HandlesCommands.HELLO.execute(stack, player, query);
					event.setCanceled(true);
					return;
				}

				HandlesCommands.HandlesCommand command = HandlesCommands.get(query);
				if (command == null) {
					HandlesCommands.sendHandlesMessage(player, stack, "Sorry, I don't seem to understand");
					event.setCanceled(true);
					return;
				}

				try {
					command.execute(stack, player, query);
				} catch (Exception e) {
					HandlesCommands.sendHandlesMessage(player, stack, "Seems like that didn't work");
					e.printStackTrace();
				}

				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void chunkEvent(ChunkEvent.Load event) {
		IChunk chunk = event.getChunk();
		IWorld world = event.getWorld();

		if (!world.isClientSide()) {
			BlockPos pos = chunk.getPos().getWorldPosition();
			Biome biome = world.getBiome(pos);
			ResourceLocation regName = biome.getRegistryName();
				if (DMACommonConfig.generateCitadel()) {
					ServerWorld serverWorld = (ServerWorld) world;
					MinecraftServer server = serverWorld.getServer();
					if (!hasCitadel(server)) {
					BlockPos pos1 = new BlockPos(-4320, 0, -4800);

					if (Objects.requireNonNull(regName).toString().contains("dmadditions:gallifrey")) {
						if (pos.equals(pos1)) {

							generateCitadel(pos, serverWorld);
							setHasCitadel(server);
						}
					}
				}
			}
		}
	}

	private static void setHasCitadel(MinecraftServer server) {
		DMAData data = Helper.getDMAData(server) == null ? new DMAData() : Helper.getDMAData(server);
		if (data != null) {
			data.hasCitadel = true;
		}
		Helper.saveDMAData(server, data);
	}

	private static boolean hasCitadel(MinecraftServer server) {
		DMAData data = Helper.getDMAData(server);
		return data != null && data.hasCitadel;
	}

	private static void generateCitadel(BlockPos pos, ServerWorld world) {
		BlockState[] states = new BlockState[]{Blocks.STRUCTURE_VOID.defaultBlockState()};
		Schematic schematic = SchematicUtils.loadSchematic("citadel", SchematicUtils.FileLocation.INTERNAL);
		Helper.generateSchematic(SchematicUtils.GenerationQueue.CITADEL,
			world, pos, schematic, states);
	}

	@SubscribeEvent
	public static void entityDeathEvent(LivingDeathEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (entity instanceof DalekEntity) {
			World world = entity.level;
			Random random = entity.getRandom();
			if (random.nextInt(10) < 2) {
				if (DMAEntities.DALEK_MUTANT != null) {
					DalekMutantEntity mutant = new DalekMutantEntity(world);
					world.addFreshEntity(mutant);
				}
			}
		}
	}

	@SubscribeEvent
	public static void tardisEvent(TardisEvent.Mat event) {
		if (DMAdditions.hasNTM() && DMAdditions.isDev()) {
			World world = event.getWorld();
			PlayerEntity player = event.getPlayer();;
			TardisFlightData flightData = event.getFlightData();
			BlockPos pos = event.getBlockPos();
			if (!world.isClientSide()) {
				List<TileEntity> possibleHazards = WorldHelper.getTEsInChunks((ServerWorld) world, new ChunkPos(pos), 3);
				possibleHazards.removeIf((tex) -> !(tex instanceof IAffectTARDISLanding));

				for (TileEntity te : possibleHazards) {
					IAffectTARDISLanding affect = (IAffectTARDISLanding) te;
					if (pos.closerThan(te.getBlockPos(), affect.getEffectiveRange())) {
						if (te instanceof TransductionBarrierTile) {
							Location coord = affectDMTardis(world,
								(TransductionBarrierTile) te, player, flightData);
							if (coord != null) {
								event.setCanceled(true);

								flightData.setPos(coord.getBlockPosition());
								flightData.setDimensionKey(coord.dimensionWorldKey());
							}
						}
					}
				}
			}
		}
	}

	private static Location affectDMTardis(World world, TransductionBarrierTile barrierTile, PlayerEntity player, TardisFlightData data) {
		if (!world.isClientSide()) {
			if (barrierTile.getBlockState().hasProperty(TransductionBarrierBlock.ACTIVATED) && barrierTile.getBlockState().getValue(TransductionBarrierBlock.ACTIVATED)) {
				Random rand = world.random;
				if (data != null) {
					BlockPos pos = data.getPos();

					BlockPos landSpot = TardisActionList.getLandingPosition((ServerWorld) world,
						new BlockPos(pos.getX() + rand.nextInt(10) * 10, pos.getY() + rand.nextInt(10) * 10,
							pos.getZ() + rand.nextInt(10) * 10), data.getRotationAngle());

					barrierTile.onBlockedTARDIS(null);
					ChatUtil.sendCompletedMsg(player, new TranslationTextComponent("text.tardis.transduction.line3"), ChatUtil.MessageType.STATUS_BAR);

					return new Location(landSpot, world.dimension());
				}
			}
		}
		return null;
	}

	@SubscribeEvent
	public static void attachPlayerCap(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity) {
			event.addCapability(PLAYER_DATA_CAP, new IPlayerRegenCap.Provider(new PlayerRegenCapability((PlayerEntity) event.getObject())));
		}
	}

	@SubscribeEvent
	public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof LivingEntity)) return;

		if (entity.level.isClientSide) return;

		Gravity.changeGravity((LivingEntity) entity, event.getDimension(), entity.level.dimension());
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		World world = event.getWorld();
		if (world.isClientSide) return;

		Entity entity = event.getEntity();
		if (!(entity instanceof LivingEntity)) return;
		if (entity.tickCount > 0) return;

		RegistryKey<World> dimension = event.getWorld().dimension();
		if (!Gravity.DIMENSION_GRAVITY.containsKey(dimension)) return;

		Gravity.changeGravity((LivingEntity) entity, dimension, null);
	}

}
