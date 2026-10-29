package com.parrymod;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

/** The parry effect image as a billboard particle: always faces the camera, so it is visible from every angle. */
public class ParryParticle extends TextureSheetParticle {
    private static final float START_SIZE = 0.45F;
    private static final float GROW = 0.35F;

    protected ParryParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.lifetime = 6;
        this.quadSize = START_SIZE;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = 0.0D;
        this.yd = 0.0D;
        this.zd = 0.0D;
        this.pickSprite(sprites);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }
        float t = (float) this.age / (float) this.lifetime;
        this.alpha = 1.0F - t;
        this.quadSize = START_SIZE + GROW * t;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float partialTick) {
        return 15728880; // full bright
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public ParryParticle createParticle(SimpleParticleType type, ClientLevel level,
                                            double x, double y, double z,
                                            double dx, double dy, double dz) {
            return new ParryParticle(level, x, y, z, sprites);
        }
    }
}
