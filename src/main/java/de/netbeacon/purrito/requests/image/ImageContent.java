package de.netbeacon.purrito.requests.image;

import de.netbeacon.purrito.requests.endpoints.AnimeImageEndpoints;

import java.util.Random;

public interface ImageContent{

	AnimeImageEndpoints getEndpoint();

	AnimeImageEndpoints[] getEndpoints();

	enum SFW implements ImageContent{

		BACKGROUND(AnimeImageEndpoints.SFW_Background),
		BITE(AnimeImageEndpoints.SFW_Bite),
		BLUSH(AnimeImageEndpoints.SFW_Blush),
		CRY(AnimeImageEndpoints.SFW_Cry),
		CUDDLE(AnimeImageEndpoints.SFW_Cuddle),
		DAMCE(AnimeImageEndpoints.SFW_Dance),
		EEVEE(AnimeImageEndpoints.SFW_Eevee),
		FEED(AnimeImageEndpoints.SFW_Feed),
		FLUFF(AnimeImageEndpoints.SFW_Fluff),
		HOLO(AnimeImageEndpoints.SFW_Holo),
		HUG(AnimeImageEndpoints.SFW_Hug),
		ICON(AnimeImageEndpoints.SFW_Icon),
		KISS(AnimeImageEndpoints.SFW_Kiss),
		KITSUNE(AnimeImageEndpoints.SFW_Kitsune),
		LICK(AnimeImageEndpoints.SFW_Lick),
		NEKO(AnimeImageEndpoints.SFW_Neko),
		OKAMI(AnimeImageEndpoints.SFW_Okami),
		PAT(AnimeImageEndpoints.SFW_Pat),
		POKE(AnimeImageEndpoints.SFW_Poke),
		SLAP(AnimeImageEndpoints.SFW_Slap),
		SMILE(AnimeImageEndpoints.SFW_Smile),
		TAIL(AnimeImageEndpoints.SFW_Tail),
		TICKLE(AnimeImageEndpoints.SFW_Tickle),
		RANDOM(
			AnimeImageEndpoints.SFW_Bite,
			AnimeImageEndpoints.SFW_Blush,
			AnimeImageEndpoints.SFW_Cry,
			AnimeImageEndpoints.SFW_Cuddle,
			AnimeImageEndpoints.SFW_Dance,
			AnimeImageEndpoints.SFW_Eevee,
			AnimeImageEndpoints.SFW_Feed,
			AnimeImageEndpoints.SFW_Fluff,
			AnimeImageEndpoints.SFW_Holo,
			AnimeImageEndpoints.SFW_Hug,
			AnimeImageEndpoints.SFW_Icon,
			AnimeImageEndpoints.SFW_Kiss,
			AnimeImageEndpoints.SFW_Kitsune,
			AnimeImageEndpoints.SFW_Lick,
			AnimeImageEndpoints.SFW_Neko,
			AnimeImageEndpoints.SFW_Okami,
			AnimeImageEndpoints.SFW_Pat,
			AnimeImageEndpoints.SFW_Poke,
			AnimeImageEndpoints.SFW_Slap,
			AnimeImageEndpoints.SFW_Smile,
			AnimeImageEndpoints.SFW_Tail,
			AnimeImageEndpoints.SFW_Tickle
		);

		private final AnimeImageEndpoints[] animeImageEndpoints;
		private final Random random;

		SFW(AnimeImageEndpoints... animeImageEndpoints){
			this.animeImageEndpoints = animeImageEndpoints;
			this.random = animeImageEndpoints.length == 1 ? null : new Random();
		}


		@Override
		public AnimeImageEndpoints getEndpoint(){
			if(animeImageEndpoints.length == 1){
				return animeImageEndpoints[0];
			}
			return animeImageEndpoints[random.nextInt(animeImageEndpoints.length)];
		}

		@Override
		public AnimeImageEndpoints[] getEndpoints(){
			return animeImageEndpoints;
		}
	}

	enum NSFW implements ImageContent{
		ANAL(AnimeImageEndpoints.NSFW_Anal),
		BLOWJOB(AnimeImageEndpoints.NSFW_Blowjob),
		CUM(AnimeImageEndpoints.NSFW_Cum),
		FUCK(AnimeImageEndpoints.NSFW_Fuck),
		NEKO(AnimeImageEndpoints.NSFW_Neko),
		PUSSYLICK(AnimeImageEndpoints.NSFW_Pussylick),
		SOLO(AnimeImageEndpoints.NSFW_Solo),
		THREESOME(
			AnimeImageEndpoints.NSFW_Threesome_fff,
			AnimeImageEndpoints.NSFW_Threesome_ffm,
			AnimeImageEndpoints.NSFW_Threesome_mmf
		),
		THREESOME_FFF(AnimeImageEndpoints.NSFW_Threesome_fff),
		THREESOME_FFM(AnimeImageEndpoints.NSFW_Threesome_ffm),
		THREESOME_MMF(AnimeImageEndpoints.NSFW_Threesome_mmf),
		YAOI(AnimeImageEndpoints.NSFW_Yaoi),
		YURI(AnimeImageEndpoints.NSFW_Yuri),
		RANDOM(
			AnimeImageEndpoints.NSFW_Anal,
			AnimeImageEndpoints.NSFW_Blowjob,
			AnimeImageEndpoints.NSFW_Cum,
			AnimeImageEndpoints.NSFW_Fuck,
			AnimeImageEndpoints.NSFW_Neko,
			AnimeImageEndpoints.NSFW_Pussylick,
			AnimeImageEndpoints.NSFW_Solo,
			AnimeImageEndpoints.NSFW_Threesome_fff,
			AnimeImageEndpoints.NSFW_Threesome_ffm,
			AnimeImageEndpoints.NSFW_Threesome_mmf,
			AnimeImageEndpoints.NSFW_Yaoi,
			AnimeImageEndpoints.NSFW_Yuri
		);

		private final AnimeImageEndpoints[] animeImageEndpoints;
		private final Random random;

		NSFW(AnimeImageEndpoints... animeImageEndpoints){
			this.animeImageEndpoints = animeImageEndpoints;
			this.random = animeImageEndpoints.length == 1 ? null : new Random();
		}

		@Override
		public AnimeImageEndpoints getEndpoint(){
			if(animeImageEndpoints.length == 1){
				return animeImageEndpoints[0];
			}
			return animeImageEndpoints[random.nextInt(animeImageEndpoints.length)];
		}

		@Override
		public AnimeImageEndpoints[] getEndpoints(){
			return animeImageEndpoints;
		}

	}

}
