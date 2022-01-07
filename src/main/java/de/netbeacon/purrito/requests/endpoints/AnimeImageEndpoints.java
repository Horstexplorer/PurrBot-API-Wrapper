package de.netbeacon.purrito.requests.endpoints;

import de.netbeacon.purrito.requests.image.ImageType;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

public enum AnimeImageEndpoints{
	SFW_Background(new Imp("/img/sfw/background/", false, ImageType.IMAGE)),
	SFW_Bite(new Imp("/img/sfw/bite/", false, ImageType.GIF)),
	SFW_Blush(new Imp("/img/sfw/blush/", false, ImageType.GIF)),
	SFW_Cry(new Imp("/img/sfw/cry/", false, ImageType.GIF)),
	SFW_Cuddle(new Imp("/img/sfw/cuddle/", false, ImageType.GIF)),
	SFW_Dance(new Imp("/img/sfw/dance/", false, ImageType.GIF)),
	SFW_Eevee(new Imp("/img/sfw/eevee/", false, ImageType.IMAGE, ImageType.GIF)),
	SFW_Feed(new Imp("/img/sfw/feed/", false, ImageType.GIF)),
	SFW_Fluff(new Imp("/img/sfw/fluff/", false, ImageType.GIF)),
	SFW_Holo(new Imp("/img/sfw/", false, ImageType.IMAGE)),
	SFW_Hug(new Imp("/img/sfw/hug/", false, ImageType.GIF)),
	SFW_Icon(new Imp("/img/sfw/icon/", false, ImageType.IMAGE)),
	SFW_Kiss(new Imp("/img/sfw/kiss/", false, ImageType.GIF)),
	SFW_Kitsune(new Imp("/img/sfw/kitsune/", false, ImageType.IMAGE)),
	SFW_Lick(new Imp("/img/sfw/lick/", false, ImageType.GIF)),
	SFW_Neko(new Imp("/img/sfw/neko/", false, ImageType.IMAGE, ImageType.GIF)),
	SFW_Okami(new Imp("/img/sfw/okami/", false, ImageType.IMAGE)),
	SFW_Pat(new Imp("/img/sfw/pat/", false, ImageType.GIF)),
	SFW_Poke(new Imp("/img/sfw/poke/", false, ImageType.GIF)),
	SFW_Senko(new Imp("/img/sfw/senko/", false, ImageType.IMAGE)),
	SFW_Slap(new Imp("/img/sfw/slap/", false, ImageType.GIF)),
	SFW_Smile(new Imp("/img/sfw/smile/", false, ImageType.GIF)),
	SFW_Tail(new Imp("/img/sfw/tail/", false, ImageType.GIF)),
	SFW_Tickle(new Imp("/img/sfw/tickle/", false, ImageType.GIF)),

	NSFW_Anal(new Imp("/img/nsfw/anal/", true, ImageType.GIF)),
	NSFW_Blowjob(new Imp("/img/nsfw/blowjob/", true, ImageType.GIF)),
	NSFW_Cum(new Imp("/img/nsfw/cum/", true, ImageType.GIF)),
	NSFW_Fuck(new Imp("/img/nsfw/fuck/", true, ImageType.GIF)),
	NSFW_Neko(new Imp("/img/nsfw/neko/", true, ImageType.IMAGE, ImageType.GIF)),
	NSFW_Pussylick(new Imp("/img/nsfw/pussylick/", true, ImageType.GIF)),
	NSFW_Solo(new Imp("/img/nsfw/solo/", true, ImageType.GIF)),
	NSFW_Threesome_fff(new Imp("/img/nsfw/threesome_fff/", true, ImageType.GIF)),
	NSFW_Threesome_ffm(new Imp("/img/nsfw/threesome_ffm/", true, ImageType.GIF)),
	NSFW_Threesome_mmf(new Imp("/img/nsfw/threesome_mmf/", true, ImageType.GIF)),
	NSFW_Yaoi(new Imp("/img/nsfw/yaoi/", true, ImageType.GIF)),
	NSFW_Yuri(new Imp("/img/nsfw/yuri/", true, ImageType.GIF)),
	;


	private final Imp imp;

	AnimeImageEndpoints(Imp imp){
		this.imp = imp;
	}

	public Imp getEndpointImpData(){
		return imp;
	}

	public static class Imp extends APIEndpoint{

		private final boolean isNSFW;
		private final List<ImageType> availableTypes;

		private Imp(String path, boolean isNSFW, ImageType... availableTypes){
			super(RequestMethod.GET, path);
			this.isNSFW = isNSFW;
			this.availableTypes = List.of(availableTypes);
		}

		public boolean isNSFW(){
			return isNSFW;
		}

		public List<ImageType> getAvailableTypes(){
			return availableTypes;
		}

		public boolean isAvailableFor(ImageType imageType){
			if(imageType == ImageType.RANDOM){
				return true;
			}
			return new HashSet<>(availableTypes).contains(imageType);
		}

		public ImageType getRandomAvailableType(){
			return availableTypes.get(new Random().nextInt(availableTypes.size()));
		}

	}

}
