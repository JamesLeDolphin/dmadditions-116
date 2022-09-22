package com.jdolphin.dmadditions.init;

import com.swdteam.common.init.DMProjectiles;

import java.util.ArrayList;
import java.util.List;

public class DMAdditionsProjectiles extends DMProjectiles {
    private static List<Laser> LASERS = new ArrayList();
    public static DMProjectiles.Laser PURPLE_LASER;

    public static void init() {
        PURPLE_LASER = addLaser(217,130,181);

    }

    private static DMProjectiles.Laser addLaser(int r, int g, int b) {
        DMProjectiles.Laser l = new DMProjectiles.Laser(LASERS.size(), (float) r / 255.0F, (float) g / 255.0F, (float) b / 255.0F);
        LASERS.add(l);
        return l;
    }

    private static DMProjectiles.Laser addLaser(boolean renders) {
        DMProjectiles.Laser l = new DMProjectiles.Laser(LASERS.size(), renders);
        LASERS.add(l);
        return l;
    }
}
