/*
 *     Copyright 2021 Horstexplorer @ https://www.netbeacon.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.netbeacon.purrito.qol.typewrap;

import de.netbeacon.purrito.core.request.Endpoint;
import java.util.Random;

/**
 * Contains all endpoints of the api
 */
public interface ImageType {

    /**
     * Returns the actual endpoint of the type
     *
     * @return Endpoint
     */
    Endpoint getEndpoint();

    /**
     * Contains all SFW image endpoints of the api
     */
    enum SFW implements ImageType {
        BACKGROUND(Endpoint.SFW_Background),
        BITE(Endpoint.SFW_Bite),
        BLUSH(Endpoint.SFW_Blush),
        CRY(Endpoint.SFW_Cry),
        CUDDLE(Endpoint.SFW_Cuddle),
        DANCE(Endpoint.SFW_Dance),
        EEVEE(Endpoint.SFW_Eevee),
        FEED(Endpoint.SFW_Feed),
        FLUFF(Endpoint.SFW_Fluff),
        HOLO(Endpoint.SFW_Holo),
        HUG(Endpoint.SFW_Hug),
        ICON(Endpoint.SFW_Icon),
        KISS(Endpoint.SFW_Kiss),
        KITSUNE(Endpoint.SFW_Kitsune),
        LICK(Endpoint.SFW_Lick),
        NEKO(Endpoint.SFW_Neko),
        OOKAMI(Endpoint.SFW_Ookami),
        PAT(Endpoint.SFW_Pat),
        POKE(Endpoint.SFW_Poke),
        SENKO(Endpoint.SFW_Senko),
        SLAP(Endpoint.SFW_Slap),
        SMILE(Endpoint.SFW_Smile),
        TAIL(Endpoint.SFW_Tail),
        TICKLE(Endpoint.SFW_Tickle),

        RANDOM(
                Endpoint.SFW_Bite,
                Endpoint.SFW_Blush,
                Endpoint.SFW_Cry,
                Endpoint.SFW_Cuddle,
                Endpoint.SFW_Dance,
                Endpoint.SFW_Eevee,
                Endpoint.SFW_Feed,
                Endpoint.SFW_Fluff,
                Endpoint.SFW_Holo,
                Endpoint.SFW_Hug,
                Endpoint.SFW_Icon,
                Endpoint.SFW_Kiss,
                Endpoint.SFW_Kitsune,
                Endpoint.SFW_Lick,
                Endpoint.SFW_Neko,
                Endpoint.SFW_Ookami,
                Endpoint.SFW_Pat,
                Endpoint.SFW_Poke,
                Endpoint.SFW_Senko,
                Endpoint.SFW_Slap,
                Endpoint.SFW_Smile,
                Endpoint.SFW_Tail,
                Endpoint.SFW_Tickle
        );

        private final Endpoint[] endpoints;
        private final Random random;

        SFW(Endpoint... endpoint) {
            this.endpoints = endpoint;
            this.random = endpoints.length > 1 ? new Random() : null;
        }

        @Override
        public Endpoint getEndpoint() {
            if (endpoints.length == 1) {
                return endpoints[0];
            }
            return endpoints[random.nextInt(endpoints.length)];
        }
    }

    /**
     * Contains all SFW image endpoints of the api
     */
    enum NSFW implements ImageType {
        ANAL(Endpoint.NSFW_Anal),
        BLOWJOB(Endpoint.NSFW_Blowjob),
        CUM(Endpoint.NSFW_Cum),
        FUCK(Endpoint.NSFW_Fuck),
        NEKO(Endpoint.NSFW_Neko),
        PUSSYLICK(Endpoint.NSFW_Pussylick),
        SOLO(Endpoint.NSFW_Solo),
        THREESOME(Endpoint.NSFW_Threesome_fff, Endpoint.NSFW_Threesome_ffm, Endpoint.NSFW_Threesome_mmf),
        THREESOME_FFF(Endpoint.NSFW_Threesome_fff),
        THREESOME_FFM(Endpoint.NSFW_Threesome_ffm),
        THREESOME_MMF(Endpoint.NSFW_Threesome_mmf),
        YAOI(Endpoint.NSFW_Yaoi),
        YURI(Endpoint.NSFW_Yuri),

        RANDOM(
                Endpoint.NSFW_Anal,
                Endpoint.NSFW_Blowjob,
                Endpoint.NSFW_Cum,
                Endpoint.NSFW_Fuck,
                Endpoint.NSFW_Neko,
                Endpoint.NSFW_Pussylick,
                Endpoint.NSFW_Solo,
                Endpoint.NSFW_Threesome_fff,
                Endpoint.NSFW_Threesome_ffm,
                Endpoint.NSFW_Threesome_mmf,
                Endpoint.NSFW_Yaoi,
                Endpoint.NSFW_Yuri
        );

        private final Endpoint[] endpoints;
        private final Random random;

        NSFW(Endpoint... endpoint) {
            this.endpoints = endpoint;
            this.random = endpoints.length > 1 ? new Random() : null;
        }

        @Override
        public Endpoint getEndpoint() {
            if (endpoints.length == 1) {
                return endpoints[0];
            }
            return endpoints[random.nextInt(endpoints.length)];
        }
    }
}
