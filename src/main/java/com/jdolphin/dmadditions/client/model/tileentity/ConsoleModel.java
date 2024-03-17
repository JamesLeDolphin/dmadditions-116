package com.jdolphin.dmadditions.client.model.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class ConsoleModel extends Model {
	private final ModelRenderer bone3;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer bone4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer bone5;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;
	private final ModelRenderer cube_r11;
	private final ModelRenderer bone6;
	private final ModelRenderer cube_r12;
	private final ModelRenderer cube_r13;
	private final ModelRenderer cube_r14;
	private final ModelRenderer cube_r15;
	private final ModelRenderer bone7;
	private final ModelRenderer cube_r16;
	private final ModelRenderer cube_r17;
	private final ModelRenderer cube_r18;
	private final ModelRenderer cube_r19;
	private final ModelRenderer cube_r20;
	private final ModelRenderer cube_r21;
	private final ModelRenderer bone10;
	private final ModelRenderer cube_r22;
	private final ModelRenderer cube_r23;
	private final ModelRenderer cube_r24;
	private final ModelRenderer cube_r25;
	private final ModelRenderer bone11;
	private final ModelRenderer cube_r26;
	private final ModelRenderer cube_r27;
	private final ModelRenderer cube_r28;
	private final ModelRenderer cube_r29;
	private final ModelRenderer bone8;
	private final ModelRenderer cube_r30;
	private final ModelRenderer bone12;
	private final ModelRenderer cube_r31;
	private final ModelRenderer cube_r32;
	private final ModelRenderer cube_r33;
	private final ModelRenderer cube_r34;
	private final ModelRenderer cube_r35;
	private final ModelRenderer cube_r36;
	private final ModelRenderer bone;
	private final ModelRenderer cube_r37;
	private final ModelRenderer cube_r38;
	private final ModelRenderer cube_r39;
	private final ModelRenderer cube_r40;
	private final ModelRenderer bone2;
	private final ModelRenderer cube_r41;
	private final ModelRenderer cube_r42;
	private final ModelRenderer cube_r43;
	private final ModelRenderer cube_r44;
	private final ModelRenderer cube_r45;
	private final ModelRenderer cube_r46;

	public ConsoleModel() {
		super(RenderType::entitySolid);
		texWidth = 256;
		texHeight = 256;

		bone3 = new ModelRenderer(this);
		bone3.setPos(0.0F, 24.0F, 2.0F);
		bone3.texOffs(72, 84).addBox(-6.0F, 0.0F, 2.0F, 12.0F, 0.0F, 6.0F, 0.0F, false);
		bone3.texOffs(82, 24).addBox(-6.0F, -0.05F, -12.7846F, 12.0F, 0.0F, 6.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(4.2583F, 0.0F, -0.2321F);
		bone3.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 2.0944F, 0.0F);
		cube_r1.texOffs(0, 80).addBox(-2.0F, 0.0F, -13.0F, 12.0F, 0.0F, 6.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(18.2487F, 0.0F, 1.2154F);
		bone3.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, -2.0944F, 0.0F);
		cube_r2.texOffs(24, 80).addBox(0.0F, 0.0F, 22.0F, 12.0F, 0.0F, 6.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(4.0718F, 0.0F, -0.1244F);
		bone3.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 2.0944F, 0.0F);
		cube_r3.texOffs(82, 30).addBox(-2.0F, 0.0F, 2.0F, 12.0F, 0.0F, 6.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(17.0622F, 0.0F, 2.8397F);
		bone3.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, -2.0944F, 0.0F);
		cube_r4.texOffs(48, 84).addBox(-2.0F, 0.0F, 7.0F, 12.0F, 0.0F, 6.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setPos(0.0F, 24.0F, 0.0F);


		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(-8.0F, 0.0F, 1.6067F);
		bone4.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 1.5708F, 0.0F);
		cube_r5.texOffs(0, 0).addBox(2.0F, -14.0F, -11.0F, 0.0F, 14.0F, 38.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(4.7321F, 0.0F, -4.5885F);
		bone4.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, 2.618F, 0.0F);
		cube_r6.texOffs(0, 14).addBox(2.0F, -14.0F, -25.0F, 0.0F, 14.0F, 38.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(0.9991F, 0.0F, 1.3402F);
		bone4.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, -2.618F, 0.0F);
		cube_r7.texOffs(0, 28).addBox(0.0F, -14.0F, -17.0F, 0.0F, 14.0F, 38.0F, 0.0F, false);

		bone5 = new ModelRenderer(this);
		bone5.setPos(13.0F, 23.0F, 0.0F);
		bone5.texOffs(106, 111).addBox(-16.0F, -13.0F, -5.5895F, 6.0F, 14.0F, 0.0F, 0.0F, false);
		bone5.texOffs(70, 104).addBox(-16.0F, -13.0F, 4.8028F, 6.0F, 14.0F, 0.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(-14.3349F, 1.0F, 17.3112F);
		bone5.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, -2.0944F, 0.0F);
		cube_r8.texOffs(0, 0).addBox(-19.0F, -14.0F, 2.5F, 6.0F, 14.0F, 0.0F, 0.0F, false);

		cube_r9 = new ModelRenderer(this);
		cube_r9.setPos(-13.0582F, 1.0F, 18.1155F);
		bone5.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.0F, 2.0944F, 0.0F);
		cube_r9.texOffs(0, 14).addBox(13.0F, -14.0F, 14.5F, 6.0F, 14.0F, 0.0F, 0.0F, false);

		cube_r10 = new ModelRenderer(this);
		cube_r10.setPos(-13.3971F, 1.0F, 18.3112F);
		bone5.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.0F, 2.0944F, 0.0F);
		cube_r10.texOffs(82, 104).addBox(13.0F, -14.0F, 4.5F, 6.0F, 14.0F, 0.0F, 0.0F, false);

		cube_r11 = new ModelRenderer(this);
		cube_r11.setPos(-12.9418F, 1.0F, 18.1155F);
		bone5.addChild(cube_r11);
		setRotationAngle(cube_r11, 0.0F, -2.0944F, 0.0F);
		cube_r11.texOffs(94, 111).addBox(-19.0F, -14.0F, 14.5F, 6.0F, 14.0F, 0.0F, 0.0F, false);

		bone6 = new ModelRenderer(this);
		bone6.setPos(0.0F, 24.0F, 0.0F);
		bone6.texOffs(76, 90).addBox(-9.5F, -14.0F, 16.0612F, 19.0F, 2.0F, 0.0F, 0.0F, false);
		bone6.texOffs(38, 90).addBox(-9.5F, -14.0F, -16.8468F, 19.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r12 = new ModelRenderer(this);
		cube_r12.setPos(-25.409F, -1.0F, -1.3099F);
		bone6.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.0F, 2.0944F, 0.0F);
		cube_r12.texOffs(0, 90).addBox(-23.0F, -13.0F, 38.0F, 19.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r13 = new ModelRenderer(this);
		cube_r13.setPos(-21.866F, -1.0F, -3.3564F);
		bone6.addChild(cube_r13);
		setRotationAngle(cube_r13, 0.0F, 2.0944F, 0.0F);
		cube_r13.texOffs(38, 36).addBox(-23.0F, -13.0F, 1.0F, 19.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r14 = new ModelRenderer(this);
		cube_r14.setPos(-20.2128F, -1.0F, 3.5253F);
		bone6.addChild(cube_r14);
		setRotationAngle(cube_r14, 0.0F, -2.0944F, 0.0F);
		cube_r14.texOffs(0, 36).addBox(-23.0F, -13.0F, -32.0F, 19.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r15 = new ModelRenderer(this);
		cube_r15.setPos(-20.1331F, -1.0F, 3.5713F);
		bone6.addChild(cube_r15);
		setRotationAngle(cube_r15, 0.0F, -2.0944F, 0.0F);
		cube_r15.texOffs(0, 86).addBox(-23.0F, -13.0F, 1.0F, 19.0F, 2.0F, 0.0F, 0.0F, false);

		bone7 = new ModelRenderer(this);
		bone7.setPos(0.0F, 24.0F, 0.0F);


		cube_r16 = new ModelRenderer(this);
		cube_r16.setPos(-23.8502F, 7.5503F, 8.1805F);
		bone7.addChild(cube_r16);
		setRotationAngle(cube_r16, -0.3054F, 2.0944F, 0.0F);
		cube_r16.texOffs(38, 24).addBox(-14.0F, -33.0F, 21.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r17 = new ModelRenderer(this);
		cube_r17.setPos(6.1351F, 12.3616F, -9.1306F);
		bone7.addChild(cube_r17);
		setRotationAngle(cube_r17, 0.3054F, 2.0944F, 0.0F);
		cube_r17.texOffs(76, 12).addBox(-14.0F, -33.0F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r18 = new ModelRenderer(this);
		cube_r18.setPos(-12.1351F, 12.3616F, 1.2607F);
		bone7.addChild(cube_r18);
		setRotationAngle(cube_r18, 0.3054F, -2.0944F, 0.0F);
		cube_r18.texOffs(64, 36).addBox(-17.0F, -33.0F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r19 = new ModelRenderer(this);
		cube_r19.setPos(-17.1815F, 5.6251F, -1.6528F);
		bone7.addChild(cube_r19);
		setRotationAngle(cube_r19, -0.3054F, -2.0944F, 0.0F);
		cube_r19.texOffs(64, 48).addBox(-17.0F, -19.0F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r20 = new ModelRenderer(this);
		cube_r20.setPos(9.5F, 5.6251F, 15.1171F);
		bone7.addChild(cube_r20);
		setRotationAngle(cube_r20, -0.3054F, 0.0F, 0.0F);
		cube_r20.texOffs(64, 60).addBox(-19.0F, -19.0F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r21 = new ModelRenderer(this);
		cube_r21.setPos(9.5F, -0.9904F, 5.0801F);
		bone7.addChild(cube_r21);
		setRotationAngle(cube_r21, 0.3054F, 0.0F, 0.0F);
		cube_r21.texOffs(64, 72).addBox(-19.0F, -19.0F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		bone10 = new ModelRenderer(this);
		bone10.setPos(0.0F, 24.0F, 0.0F);
		bone10.texOffs(56, 104).addBox(-3.0F, -28.6756F, 3.8156F, 6.0F, 11.0F, 1.0F, 0.0F, false);
		bone10.texOffs(42, 104).addBox(-3.0F, -28.6756F, -5.5767F, 6.0F, 11.0F, 1.0F, 0.0F, false);

		cube_r22 = new ModelRenderer(this);
		cube_r22.setPos(-3.9641F, -0.6756F, 11.1467F);
		bone10.addChild(cube_r22);
		setRotationAngle(cube_r22, 0.0F, 2.0944F, 0.0F);
		cube_r22.texOffs(99, 99).addBox(5.0F, -28.0F, 4.0F, 6.0F, 11.0F, 1.0F, 0.0F, false);

		cube_r23 = new ModelRenderer(this);
		cube_r23.setPos(3.465F, -0.6756F, -8.7724F);
		bone10.addChild(cube_r23);
		setRotationAngle(cube_r23, 0.0F, -2.0944F, 0.0F);
		cube_r23.texOffs(0, 101).addBox(6.0F, -28.0F, 3.0F, 6.0F, 11.0F, 1.0F, 0.0F, false);

		cube_r24 = new ModelRenderer(this);
		cube_r24.setPos(-0.465F, -0.6756F, -3.5762F);
		bone10.addChild(cube_r24);
		setRotationAngle(cube_r24, 0.0F, 2.0944F, 0.0F);
		cube_r24.texOffs(14, 103).addBox(-6.0F, -28.0F, 3.0F, 6.0F, 11.0F, 1.0F, 0.0F, false);

		cube_r25 = new ModelRenderer(this);
		cube_r25.setPos(1.6699F, -0.6756F, -2.8806F);
		bone10.addChild(cube_r25);
		setRotationAngle(cube_r25, 0.0F, -2.0944F, 0.0F);
		cube_r25.texOffs(28, 104).addBox(0.0F, -28.0F, -5.0F, 6.0F, 11.0F, 1.0F, 0.0F, false);

		bone11 = new ModelRenderer(this);
		bone11.setPos(0.5F, 24.0F, 1.675F);
		bone11.texOffs(80, 92).addBox(-4.0F, -18.5F, -1.0F, 7.0F, 1.0F, 5.0F, 0.0F, false);
		bone11.texOffs(27, 98).addBox(-4.0F, -18.5F, -8.1244F, 7.0F, 1.0F, 5.0F, 0.0F, false);

		cube_r26 = new ModelRenderer(this);
		cube_r26.setPos(-1.9586F, 0.0F, -1.9129F);
		bone11.addChild(cube_r26);
		setRotationAngle(cube_r26, 0.0F, 2.0944F, 0.0F);
		cube_r26.texOffs(32, 92).addBox(-4.1F, -18.5F, 2.4F, 7.0F, 1.0F, 5.0F, 0.0F, false);

		cube_r27 = new ModelRenderer(this);
		cube_r27.setPos(2.0906F, 0.0F, 0.1263F);
		bone11.addChild(cube_r27);
		setRotationAngle(cube_r27, 0.0F, -2.0944F, 0.0F);
		cube_r27.texOffs(56, 92).addBox(-4.1F, -18.5F, 4.4F, 7.0F, 1.0F, 5.0F, 0.0F, false);

		cube_r28 = new ModelRenderer(this);
		cube_r28.setPos(-7.2624F, 0.0F, 1.1493F);
		bone11.addChild(cube_r28);
		setRotationAngle(cube_r28, 0.0F, 2.0944F, 0.0F);
		cube_r28.texOffs(51, 98).addBox(-4.1F, -18.5F, 1.4F, 7.0F, 1.0F, 5.0F, 0.0F, false);

		cube_r29 = new ModelRenderer(this);
		cube_r29.setPos(5.6624F, 0.0F, 2.1885F);
		bone11.addChild(cube_r29);
		setRotationAngle(cube_r29, 0.0F, -2.0944F, 0.0F);
		cube_r29.texOffs(75, 98).addBox(-4.1F, -18.5F, 1.4F, 7.0F, 1.0F, 5.0F, 0.0F, false);

		bone8 = new ModelRenderer(this);
		bone8.setPos(0.0F, 24.0F, 0.0F);
		bone8.texOffs(102, 84).addBox(-2.0F, -19.0F, -2.3933F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		bone8.texOffs(16, 84).addBox(0.0F, -28.0F, -4.4F, 0.0F, 9.0F, 8.0F, 0.0F, false);

		cube_r30 = new ModelRenderer(this);
		cube_r30.setPos(0.0F, 0.0F, -0.4F);
		bone8.addChild(cube_r30);
		setRotationAngle(cube_r30, 0.0F, -1.5708F, 0.0F);
		cube_r30.texOffs(0, 84).addBox(0.0F, -28.0F, -4.0F, 0.0F, 9.0F, 8.0F, 0.0F, false);

		bone12 = new ModelRenderer(this);
		bone12.setPos(0.0F, 24.0F, 0.0F);


		cube_r31 = new ModelRenderer(this);
		cube_r31.setPos(-12.1351F, 12.3616F, 1.2607F);
		bone12.addChild(cube_r31);
		setRotationAngle(cube_r31, 0.3054F, -2.0944F, 0.0F);
		cube_r31.texOffs(0, 0).addBox(-17.0F, -33.25F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r32 = new ModelRenderer(this);
		cube_r32.setPos(-23.8502F, 7.5503F, 8.1805F);
		bone12.addChild(cube_r32);
		setRotationAngle(cube_r32, -0.3054F, 2.0944F, 0.0F);
		cube_r32.texOffs(0, 12).addBox(-14.0F, -33.25F, 21.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r33 = new ModelRenderer(this);
		cube_r33.setPos(9.5F, -0.9904F, 5.0801F);
		bone12.addChild(cube_r33);
		setRotationAngle(cube_r33, 0.3054F, 0.0F, 0.0F);
		cube_r33.texOffs(0, 24).addBox(-19.0F, -19.25F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r34 = new ModelRenderer(this);
		cube_r34.setPos(9.5F, 5.6251F, 15.1171F);
		bone12.addChild(cube_r34);
		setRotationAngle(cube_r34, -0.3054F, 0.0F, 0.0F);
		cube_r34.texOffs(38, 0).addBox(-19.0F, -19.25F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r35 = new ModelRenderer(this);
		cube_r35.setPos(6.1351F, 12.3616F, -9.1306F);
		bone12.addChild(cube_r35);
		setRotationAngle(cube_r35, 0.3054F, 2.0944F, 0.0F);
		cube_r35.texOffs(76, 0).addBox(-14.0F, -33.25F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r36 = new ModelRenderer(this);
		cube_r36.setPos(-17.1815F, 5.6251F, -1.6528F);
		bone12.addChild(cube_r36);
		setRotationAngle(cube_r36, -0.3054F, -2.0944F, 0.0F);
		cube_r36.texOffs(38, 12).addBox(-17.0F, -19.25F, -17.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 24.0F, 0.0F);
		bone.texOffs(206, 0).addBox(-9.5F, -13.999F, -16.8468F, 19.0F, 0.0F, 12.0F, 0.0F, false);
		bone.texOffs(206, 0).addBox(-9.5F, -13.999F, 4.0622F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r37 = new ModelRenderer(this);
		cube_r37.setPos(-28.6865F, 1.001F, 24.8301F);
		bone.addChild(cube_r37);
		setRotationAngle(cube_r37, 0.0F, 2.0944F, 0.0F);
		cube_r37.texOffs(206, 0).addBox(-2.0F, -15.0F, 21.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r38 = new ModelRenderer(this);
		cube_r38.setPos(-10.5788F, 1.001F, 14.3756F);
		bone.addChild(cube_r38);
		setRotationAngle(cube_r38, 0.0F, 2.0944F, 0.0F);
		cube_r38.texOffs(206, 0).addBox(-2.0F, -15.0F, 21.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r39 = new ModelRenderer(this);
		cube_r39.setPos(18.0788F, 1.001F, 1.3853F);
		bone.addChild(cube_r39);
		setRotationAngle(cube_r39, 0.0F, -2.0944F, 0.0F);
		cube_r39.texOffs(206, 0).addBox(-2.0F, -15.0F, 21.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		cube_r40 = new ModelRenderer(this);
		cube_r40.setPos(19.7321F, 1.001F, 2.3397F);
		bone.addChild(cube_r40);
		setRotationAngle(cube_r40, 0.0F, -2.0944F, 0.0F);
		cube_r40.texOffs(206, 0).addBox(-2.0F, -15.0F, 2.0F, 19.0F, 0.0F, 12.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setPos(0.0F, 24.0F, 0.0F);


		cube_r41 = new ModelRenderer(this);
		cube_r41.setPos(21.1473F, -16.9334F, -11.1419F);
		bone2.addChild(cube_r41);
		setRotationAngle(cube_r41, 0.5672F, 0.0436F, 0.9163F);
		cube_r41.texOffs(0, 117).addBox(-16.4364F, 17.8255F, -18.6378F, 1.0F, 1.0F, 12.0F, 0.0F, false);

		cube_r42 = new ModelRenderer(this);
		cube_r42.setPos(-11.9961F, -1.7076F, 9.6567F);
		bone2.addChild(cube_r42);
		setRotationAngle(cube_r42, -0.2182F, -0.5672F, 0.8727F);
		cube_r42.texOffs(0, 117).addBox(-11.1021F, -19.5249F, -28.6445F, 1.0F, 1.0F, 12.0F, 0.0F, false);

		cube_r43 = new ModelRenderer(this);
		cube_r43.setPos(-1.5548F, -16.3316F, -1.3092F);
		bone2.addChild(cube_r43);
		setRotationAngle(cube_r43, -0.5672F, -0.2182F, 0.7418F);
		cube_r43.texOffs(0, 117).addBox(-1.3187F, -4.2409F, 6.1271F, 1.0F, 1.0F, 12.0F, 0.0F, false);

		cube_r44 = new ModelRenderer(this);
		cube_r44.setPos(-4.5565F, -1.3986F, -7.2724F);
		bone2.addChild(cube_r44);
		setRotationAngle(cube_r44, -0.1745F, -2.5744F, 0.829F);
		cube_r44.texOffs(0, 117).addBox(12.0F, -16.0F, -22.0F, 1.0F, 1.0F, 12.0F, 0.0F, false);

		cube_r45 = new ModelRenderer(this);
		cube_r45.setPos(6.4329F, -9.9829F, -11.7362F);
		bone2.addChild(cube_r45);
		setRotationAngle(cube_r45, -0.7854F, 0.0F, -0.3054F);
		cube_r45.texOffs(0, 136).addBox(-22.25F, -16.625F, -0.575F, 12.0F, 1.0F, 1.0F, 0.0F, false);

		cube_r46 = new ModelRenderer(this);
		cube_r46.setPos(-1.0842F, -5.3308F, -7.4936F);
		bone2.addChild(cube_r46);
		setRotationAngle(cube_r46, -0.7854F, 0.0F, 0.3054F);
		cube_r46.texOffs(0, 136).addBox(3.75F, -15.625F, -5.575F, 12.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}