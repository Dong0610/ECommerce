package dong.duan.ecommerce.library

import android.content.Context
import androidx.annotation.ColorInt
import dong.duan.ecommerce.R


data class ColorFilter(val name: String, var value: Int)

fun get_all_colorsxml(context: Context): List<ColorFilter> {
    val colors: MutableList<ColorFilter> = mutableListOf()
    colors.add(ColorFilter("appcolor", context.resources.getColor(R.color.appcolor)))
    colors.add(ColorFilter("white", context.resources.getColor(R.color.white)))
    colors.add(ColorFilter("ivory", context.resources.getColor(R.color.ivory)))
    colors.add(ColorFilter("lightyellow", context.resources.getColor(R.color.lightyellow)))
    colors.add(ColorFilter("yellow", context.resources.getColor(R.color.yellow)))
    colors.add(ColorFilter("snow", context.resources.getColor(R.color.snow)))
    colors.add(ColorFilter("floralwhite", context.resources.getColor(R.color.floralwhite)))
    colors.add(ColorFilter("lemonchiffon", context.resources.getColor(R.color.lemonchiffon)))
    colors.add(ColorFilter("cornsilk", context.resources.getColor(R.color.cornsilk)))
    colors.add(ColorFilter("seashell", context.resources.getColor(R.color.seashell)))
    colors.add(ColorFilter("lavenderblush", context.resources.getColor(R.color.lavenderblush)))
    colors.add(ColorFilter("papayawhip", context.resources.getColor(R.color.papayawhip)))
    colors.add(ColorFilter("blanchedalmond", context.resources.getColor(R.color.blanchedalmond)))
    colors.add(ColorFilter("mistyrose", context.resources.getColor(R.color.mistyrose)))
    colors.add(ColorFilter("bisque", context.resources.getColor(R.color.bisque)))
    colors.add(ColorFilter("moccasin", context.resources.getColor(R.color.moccasin)))
    colors.add(ColorFilter("navajowhite", context.resources.getColor(R.color.navajowhite)))
    colors.add(ColorFilter("peachpuff", context.resources.getColor(R.color.peachpuff)))
    colors.add(ColorFilter("gold", context.resources.getColor(R.color.gold)))
    colors.add(ColorFilter("pink", context.resources.getColor(R.color.pink)))
    colors.add(ColorFilter("lightpink", context.resources.getColor(R.color.lightpink)))
    colors.add(ColorFilter("orange", context.resources.getColor(R.color.orange)))
    colors.add(ColorFilter("lightsalmon", context.resources.getColor(R.color.lightsalmon)))
    colors.add(ColorFilter("darkorange", context.resources.getColor(R.color.darkorange)))
    colors.add(ColorFilter("coral", context.resources.getColor(R.color.coral)))
    colors.add(ColorFilter("hotpink", context.resources.getColor(R.color.hotpink)))
    colors.add(ColorFilter("tomato", context.resources.getColor(R.color.tomato)))
    colors.add(ColorFilter("orangered", context.resources.getColor(R.color.orangered)))
    colors.add(ColorFilter("deeppink", context.resources.getColor(R.color.deeppink)))
    colors.add(ColorFilter("fuchsia", context.resources.getColor(R.color.fuchsia)))
    colors.add(ColorFilter("magenta", context.resources.getColor(R.color.magenta)))
    colors.add(ColorFilter("red", context.resources.getColor(R.color.red)))
    colors.add(ColorFilter("oldlace", context.resources.getColor(R.color.oldlace)))
    colors.add(
        ColorFilter(
            "lightgoldenrodyellow",
            context.resources.getColor(R.color.lightgoldenrodyellow)
        )
    )
    colors.add(ColorFilter("linen", context.resources.getColor(R.color.linen)))
    colors.add(ColorFilter("antiquewhite", context.resources.getColor(R.color.antiquewhite)))
    colors.add(ColorFilter("salmon", context.resources.getColor(R.color.salmon)))
    colors.add(ColorFilter("ghostwhite", context.resources.getColor(R.color.ghostwhite)))
    colors.add(ColorFilter("mintcream", context.resources.getColor(R.color.mintcream)))
    colors.add(ColorFilter("whitesmoke", context.resources.getColor(R.color.whitesmoke)))
    colors.add(ColorFilter("beige", context.resources.getColor(R.color.beige)))
    colors.add(ColorFilter("wheat", context.resources.getColor(R.color.wheat)))
    colors.add(ColorFilter("sandybrown", context.resources.getColor(R.color.sandybrown)))
    colors.add(ColorFilter("azure", context.resources.getColor(R.color.azure)))
    colors.add(ColorFilter("honeydew", context.resources.getColor(R.color.honeydew)))
    colors.add(ColorFilter("aliceblue", context.resources.getColor(R.color.aliceblue)))
    colors.add(ColorFilter("khaki", context.resources.getColor(R.color.khaki)))
    colors.add(ColorFilter("lightcoral", context.resources.getColor(R.color.lightcoral)))
    colors.add(ColorFilter("palegoldenrod", context.resources.getColor(R.color.palegoldenrod)))
    colors.add(ColorFilter("violet", context.resources.getColor(R.color.violet)))
    colors.add(ColorFilter("darksalmon", context.resources.getColor(R.color.darksalmon)))
    colors.add(ColorFilter("lavender", context.resources.getColor(R.color.lavender)))
    colors.add(ColorFilter("lightcyan", context.resources.getColor(R.color.lightcyan)))
    colors.add(ColorFilter("burlywood", context.resources.getColor(R.color.burlywood)))
    colors.add(ColorFilter("plum", context.resources.getColor(R.color.plum)))
    colors.add(ColorFilter("gainsboro", context.resources.getColor(R.color.gainsboro)))
    colors.add(ColorFilter("crimson", context.resources.getColor(R.color.crimson)))
    colors.add(ColorFilter("palevioletred", context.resources.getColor(R.color.palevioletred)))
    colors.add(ColorFilter("goldenrod", context.resources.getColor(R.color.goldenrod)))
    colors.add(ColorFilter("orchid", context.resources.getColor(R.color.orchid)))
    colors.add(ColorFilter("thistle", context.resources.getColor(R.color.thistle)))
    colors.add(ColorFilter("lightgray", context.resources.getColor(R.color.lightgray)))
    colors.add(ColorFilter("tan", context.resources.getColor(R.color.tan)))
    colors.add(ColorFilter("chocolate", context.resources.getColor(R.color.chocolate)))
    colors.add(ColorFilter("peru", context.resources.getColor(R.color.peru)))
    colors.add(ColorFilter("indianred", context.resources.getColor(R.color.indianred)))
    colors.add(ColorFilter("mediumvioletred", context.resources.getColor(R.color.mediumvioletred)))
    colors.add(ColorFilter("silver", context.resources.getColor(R.color.silver)))
    colors.add(ColorFilter("darkkhaki", context.resources.getColor(R.color.darkkhaki)))
    colors.add(ColorFilter("rosybrown", context.resources.getColor(R.color.rosybrown)))
    colors.add(ColorFilter("mediumorchid", context.resources.getColor(R.color.mediumorchid)))
    colors.add(ColorFilter("darkgoldenrod", context.resources.getColor(R.color.darkgoldenrod)))
    colors.add(ColorFilter("firebrick", context.resources.getColor(R.color.firebrick)))
    colors.add(ColorFilter("powderblue", context.resources.getColor(R.color.powderblue)))
    colors.add(ColorFilter("lightsteelblue", context.resources.getColor(R.color.lightsteelblue)))
    colors.add(ColorFilter("paleturquoise", context.resources.getColor(R.color.paleturquoise)))
    colors.add(ColorFilter("greenyellow", context.resources.getColor(R.color.greenyellow)))
    colors.add(ColorFilter("lightblue", context.resources.getColor(R.color.lightblue)))
    colors.add(ColorFilter("darkgray", context.resources.getColor(R.color.darkgray)))
    colors.add(ColorFilter("brown", context.resources.getColor(R.color.brown)))
    colors.add(ColorFilter("sienna", context.resources.getColor(R.color.sienna)))
    colors.add(ColorFilter("darkorchid", context.resources.getColor(R.color.darkorchid)))
    colors.add(ColorFilter("palegreen", context.resources.getColor(R.color.palegreen)))
    colors.add(ColorFilter("darkviolet", context.resources.getColor(R.color.darkviolet)))
    colors.add(ColorFilter("mediumpurple", context.resources.getColor(R.color.mediumpurple)))
    colors.add(ColorFilter("lightgreen", context.resources.getColor(R.color.lightgreen)))
    colors.add(ColorFilter("darkseagreen", context.resources.getColor(R.color.darkseagreen)))
    colors.add(ColorFilter("saddlebrown", context.resources.getColor(R.color.saddlebrown)))
    colors.add(ColorFilter("darkmagenta", context.resources.getColor(R.color.darkmagenta)))
    colors.add(ColorFilter("darkred", context.resources.getColor(R.color.darkred)))
    colors.add(ColorFilter("blueviolet", context.resources.getColor(R.color.blueviolet)))
    colors.add(ColorFilter("lightskyblue", context.resources.getColor(R.color.lightskyblue)))
    colors.add(ColorFilter("skyblue", context.resources.getColor(R.color.skyblue)))
    colors.add(ColorFilter("gray", context.resources.getColor(R.color.gray)))
    colors.add(ColorFilter("olive", context.resources.getColor(R.color.olive)))
    colors.add(ColorFilter("purple", context.resources.getColor(R.color.purple)))
    colors.add(ColorFilter("maroon", context.resources.getColor(R.color.maroon)))
    colors.add(ColorFilter("aquamarine", context.resources.getColor(R.color.aquamarine)))
    colors.add(ColorFilter("chartreuse", context.resources.getColor(R.color.chartreuse)))
    colors.add(ColorFilter("lawngreen", context.resources.getColor(R.color.lawngreen)))
    colors.add(ColorFilter("mediumslateblue", context.resources.getColor(R.color.mediumslateblue)))
    colors.add(ColorFilter("lightslategray", context.resources.getColor(R.color.lightslategray)))
    colors.add(ColorFilter("slategray", context.resources.getColor(R.color.slategray)))
    colors.add(ColorFilter("olivedrab", context.resources.getColor(R.color.olivedrab)))
    colors.add(ColorFilter("slateblue", context.resources.getColor(R.color.slateblue)))
    colors.add(ColorFilter("dimgray", context.resources.getColor(R.color.dimgray)))
    colors.add(
        ColorFilter(
            "mediumaquamarine",
            context.resources.getColor(R.color.mediumaquamarine)
        )
    )
    colors.add(ColorFilter("cornflowerblue", context.resources.getColor(R.color.cornflowerblue)))
    colors.add(ColorFilter("cadetblue", context.resources.getColor(R.color.cadetblue)))
    colors.add(ColorFilter("darkolivegreen", context.resources.getColor(R.color.darkolivegreen)))
    colors.add(ColorFilter("indigo", context.resources.getColor(R.color.indigo)))
    colors.add(ColorFilter("mediumturquoise", context.resources.getColor(R.color.mediumturquoise)))
    colors.add(ColorFilter("darkslateblue", context.resources.getColor(R.color.darkslateblue)))
    colors.add(ColorFilter("steelblue", context.resources.getColor(R.color.steelblue)))
    colors.add(ColorFilter("royalblue", context.resources.getColor(R.color.royalblue)))
    colors.add(ColorFilter("turquoise", context.resources.getColor(R.color.turquoise)))
    colors.add(ColorFilter("mediumseagreen", context.resources.getColor(R.color.mediumseagreen)))
    colors.add(ColorFilter("limegreen", context.resources.getColor(R.color.limegreen)))
    colors.add(ColorFilter("darkslategray", context.resources.getColor(R.color.darkslategray)))
    colors.add(ColorFilter("seagreen", context.resources.getColor(R.color.seagreen)))
    colors.add(ColorFilter("forestgreen", context.resources.getColor(R.color.forestgreen)))
    colors.add(ColorFilter("lightseagreen", context.resources.getColor(R.color.lightseagreen)))
    colors.add(ColorFilter("dodgerblue", context.resources.getColor(R.color.dodgerblue)))
    colors.add(ColorFilter("midnightblue", context.resources.getColor(R.color.midnightblue)))
    colors.add(ColorFilter("aqua", context.resources.getColor(R.color.aqua)))
    colors.add(ColorFilter("springgreen", context.resources.getColor(R.color.springgreen)))
    colors.add(ColorFilter("lime", context.resources.getColor(R.color.lime)))
    colors.add(
        ColorFilter(
            "mediumspringgreen",
            context.resources.getColor(R.color.mediumspringgreen)
        )
    )
    colors.add(ColorFilter("darkturquoise", context.resources.getColor(R.color.darkturquoise)))
    colors.add(ColorFilter("deepskyblue", context.resources.getColor(R.color.deepskyblue)))
    colors.add(ColorFilter("darkcyan", context.resources.getColor(R.color.darkcyan)))
    colors.add(ColorFilter("teal", context.resources.getColor(R.color.teal)))
    return colors
}

class Colors {
   companion object {
       @ColorInt
       val TRANSPARENT:Int = 0x00FFFFFF.toInt()
       @ColorInt
       val APP_COLOR: Int = 0xFF6B27F6.toInt()

       @ColorInt
       val WHITE: Int = 0xFFFFFFFF.toInt()

       @ColorInt
       val IVORY: Int = 0xFFFFFFF0.toInt()

       @ColorInt
       val LIGHT_YELLOW: Int = 0xFFFFFFE0.toInt()

       @ColorInt
       val YELLOW: Int = 0xFFFFFF00.toInt()

       @ColorInt
       val SNOW: Int = 0xFFFFFAFA.toInt()

       @ColorInt
       val FLORAL_WHITE: Int = 0xFFFFFAF0.toInt()

       @ColorInt
       val LEMON_CHIFFON: Int = 0xFFFFFACD.toInt()

       @ColorInt
       val CORNSILK: Int = 0xFFFFF8DC.toInt()

       @ColorInt
       val SEASHELL: Int = 0xFFFFF5EE.toInt()

       @ColorInt
       val LAVENDER_BLUSH: Int = 0xFFFFF0F5.toInt()

       @ColorInt
       val PAPAYA_WHIP: Int = 0xFFFFEFD5.toInt()

       @ColorInt
       val BLANCHED_ALMOND: Int = 0xFFFFEBCD.toInt()

       @ColorInt
       val MISTY_ROSE: Int = 0xFFFFE4E1.toInt()

       @ColorInt
       val BISQUE: Int = 0xFFFFE4C4.toInt()

       @ColorInt
       val MOCCASIN: Int = 0xFFFFE4B5.toInt()

       @ColorInt
       val NAVAJOWHITE: Int = 0xFFFFDEAD.toInt()

       @ColorInt
       val PEACH_PUFF: Int = 0xFFFFDAB9.toInt()

       @ColorInt
       val GOLD: Int = 0xFFFFD700.toInt()

       @ColorInt
       val PINK: Int = 0xFFFFC0CB.toInt()

       @ColorInt
       val LIGHT_PINK: Int = 0xFFFFB6C1.toInt()

       @ColorInt
       val ORANGE: Int = 0xFFFFA500.toInt()

       @ColorInt
       val LIGHT_SALMON: Int = 0xFFFFA07A.toInt()

       @ColorInt
       val DARK_ORANGE: Int = 0xFFFF8C00.toInt()

       @ColorInt
       val CORAL: Int = 0xFFFF7F50.toInt()

       @ColorInt
       val HOT_PINK: Int = 0xFFFF69B4.toInt()

       @ColorInt
       val TOMATO: Int = 0xFFFF6347.toInt()

       @ColorInt
       val ORANGE_RED: Int = 0xFFFF4500.toInt()

       @ColorInt
       val DEEP_PINK: Int = 0xFFFF1493.toInt()

       @ColorInt
       val FUCHSIA: Int = 0xFFFF00FF.toInt()

       @ColorInt
       val MAGENTA: Int = 0xFFFF00FF.toInt()

       @ColorInt
       val RED: Int = 0xFFFF0000.toInt()

       @ColorInt
       val OLD_LACE: Int = 0xFFFDF5E6.toInt()

       @ColorInt
       val LIGHT_GOLDENROD_YELLOW: Int = 0xFFFAFAD2.toInt()

       @ColorInt
       val LINEN: Int = 0xFFFAF0E6.toInt()

       @ColorInt
       val ANTIQUE_WHITE: Int = 0xFFFAEBD7.toInt()

       @ColorInt
       val SALMON: Int = 0xFFFA8072.toInt()

       @ColorInt
       val GHOST_WHITE: Int = 0xFFF8F8FF.toInt()

       @ColorInt
       val MINT_CREAM: Int = 0xFFF5FFFA.toInt()

       @ColorInt
       val WHITE_SMOKE: Int = 0xFFF5F5F5.toInt()

       @ColorInt
       val BEIGE: Int = 0xFFF5F5DC.toInt()

       @ColorInt
       val WHEAT: Int = 0xFFF5DEB3.toInt()

       @ColorInt
       val SANDY_BROWN: Int = 0xFFFAA460.toInt()

       @ColorInt
       val AZURE: Int = 0xFFF0FFFF.toInt()

       @ColorInt
       val HONEYDEW: Int = 0xFFF0FFF0.toInt()

       @ColorInt
       val ALICE_BLUE: Int = 0xFFF0F8FF.toInt()

       @ColorInt
       val KHAKI: Int = 0xFFF0E68C.toInt()

       @ColorInt
       val LIGHT_CORAL: Int = 0xFFF08080.toInt()

       @ColorInt
       val PALE_GOLDENROD: Int = 0xFFEEE8AA.toInt()

       @ColorInt
       val VIOLET: Int = 0xFFEE82EE.toInt()

       @ColorInt
       val DARK_SALMON: Int = 0xFFE9967A.toInt()

       @ColorInt
       val LAVENDER: Int = 0xFFE6E6FA.toInt()

       @ColorInt
       val LIGHT_CYAN: Int = 0xFFE0FFFF.toInt()

       @ColorInt
       val BURLYWOOD: Int = 0xFFDEB887.toInt()

       @ColorInt
       val PLUM: Int = 0xFFDDA0DD.toInt()

       @ColorInt
       val GAINSBORO: Int = 0xFFDCDCDC.toInt()

       @ColorInt
       val CRIMSON: Int = 0xFFDC143C.toInt()

       @ColorInt
       val PALE_VIOLET_RED: Int = 0xFFDB7093.toInt()

       @ColorInt
       val GOLDENROD: Int = 0xFFDAA520.toInt()

       @ColorInt
       val ORCHID: Int = 0xFFDA70D6.toInt()

       @ColorInt
       val THISTLE: Int = 0xFFD8BFD8.toInt()

       @ColorInt
       val LIGHT_GREY: Int = 0xFFD3D3D3.toInt()

       @ColorInt
       val TAN: Int = 0xFFD2B48C.toInt()

       @ColorInt
       val CHOCOLATE: Int = 0xFFD2691E.toInt()

       @ColorInt
       val PERU: Int = 0xFFCD853F.toInt()

       @ColorInt
       val INDIAN_RED: Int = 0xFFCD5C5C.toInt()

       @ColorInt
       val MEDIUM_VIOLET_RED: Int = 0xFFC71585.toInt()

       @ColorInt
       val SILVER: Int = 0xFFC0C0C0.toInt()

       @ColorInt
       val DARK_KHAKI: Int = 0xFFBDB76B.toInt()

       @ColorInt
       val ROSY_BROWN: Int = 0xFFBC8F8F.toInt()

       @ColorInt
       val MEDIUM_ORCHID: Int = 0xFFBA55D3.toInt()

       @ColorInt
       val DARK_GOLDENROD: Int = 0xFFB8860B.toInt()

       @ColorInt
       val FIREBRICK: Int = 0xFFB22222.toInt()

       @ColorInt
       val POWDER_BLUE: Int = 0xFFB0E0E6.toInt()

       @ColorInt
       val LIGHT_STEEL_BLUE: Int = 0xFFB0C4DE.toInt()

       @ColorInt
       val PALE_TURQUOISE: Int = 0xFFAFEEEE.toInt()

       @ColorInt
       val GREEN_YELLOW: Int = 0xFFADFF2F.toInt()

       @ColorInt
       val LIGHT_BLUE: Int = 0xFFADD8E6.toInt()

       @ColorInt
       val DARK_GRAY: Int = 0xFFA9A9A9.toInt()

       @ColorInt
       val BROWN: Int = 0xFFA52A2A.toInt()

       @ColorInt
       val SIENNA: Int = 0xFFA0522D.toInt()

       @ColorInt
       val YELLOW_GREEN: Int = 0xFF9ACD32.toInt()

       @ColorInt
       val DARK_ORCHID: Int = 0xFF9932CC.toInt()

       @ColorInt
       val PALE_GREEN: Int = 0xFF98FB98.toInt()

       @ColorInt
       val DARK_VIOLET: Int = 0xFF9400D3.toInt()

       @ColorInt
       val MEDIUM_PURPLE: Int = 0xFF9370DB.toInt()

       @ColorInt
       val LIGHT_GREEN: Int = 0xFF90EE90.toInt()

       @ColorInt
       val DARK_SEA_GREEN: Int = 0xFF8FBC8F.toInt()

       @ColorInt
       val SADDLE_BROWN: Int = 0xFF8B4513.toInt()

       @ColorInt
       val DARK_MAGENTA: Int = 0xFF8B008B.toInt()

       @ColorInt
       val DARK_RED: Int = 0xFF8B0000.toInt()

       @ColorInt
       val BLUE_VIOLET: Int = 0xFF8A2BE2.toInt()

       @ColorInt
       val LIGHT_SKY_BLUE: Int = 0xFF87CEFA.toInt()

       @ColorInt
       val SKY_BLUE: Int = 0xFF87CEEB.toInt()

       @ColorInt
       val GRAY: Int = 0xFF808080.toInt()

       @ColorInt
       val OLIVE: Int = 0xFF808000.toInt()

       @ColorInt
       val PURPLE: Int = 0xFF800080.toInt()

       @ColorInt
       val MAROON: Int = 0xFF800000.toInt()

       @ColorInt
       val AQUAMARINE: Int = 0xFF7FFFD4.toInt()

       @ColorInt
       val CHARTREUSE: Int = 0xFF7FFF00.toInt()

       @ColorInt
       val LAWN_GREEN: Int = 0xFF7CFC00.toInt()

       @ColorInt
       val MEDIUM_SLATE_BLUE: Int = 0xFF7B68EE.toInt()

       @ColorInt
       val LIGHT_SLATE_GRAY: Int = 0xFF778899.toInt()

       @ColorInt
       val SLATE_GRAY: Int = 0xFF708090.toInt()

       @ColorInt
       val OLIVE_DRAB: Int = 0xFF6B8E23.toInt()

       @ColorInt
       val SLATE_BLUE: Int = 0xFF6A5ACD.toInt()

       @ColorInt
       val DIM_GRAY: Int = 0xFF696969.toInt()

       @ColorInt
       val MEDIUM_AQUAMARINE: Int = 0xFF66CDAA.toInt()

       @ColorInt
       val CORNFLOWER_BLUE: Int = 0xFF6495ED.toInt()

       @ColorInt
       val CADET_BLUE: Int = 0xFF5F9EA0.toInt()

       @ColorInt
       val DARK_OLIVE_GREEN: Int = 0xFF556B2F.toInt()

       @ColorInt
       val INDIGO: Int = 0xFF4B0082.toInt()

       @ColorInt
       val MEDIUM_TURQUOISE: Int = 0xFF48D1CC.toInt()

       @ColorInt
       val DARK_SLATE_BLUE: Int = 0xFF483D8B.toInt()

       @ColorInt
       val STEEL_BLUE: Int = 0xFF4682B4.toInt()

       @ColorInt
       val ROYAL_BLUE: Int = 0xFF4169E1.toInt()

       @ColorInt
       val TURQUOISE: Int = 0xFF40E0D0.toInt()

       @ColorInt
       val MEDIUM_SEA_GREEN: Int = 0xFF3CB371.toInt()

       @ColorInt
       val LIME_GREEN: Int = 0xFF32CD32.toInt()

       @ColorInt
       val DARK_TURQUOISE: Int = 0xFF00CED1.toInt()

       @ColorInt
       val CYAN: Int = 0xFF00FFFF.toInt()

       @ColorInt
       val SPRING_GREEN: Int = 0xFF00FF7F.toInt()

       @ColorInt
       val LIME: Int = 0xFF00FF00.toInt()

       @ColorInt
       val MEDIUM_SPRING_GREEN: Int = 0xFF00FA9A.toInt()

       @ColorInt
       val DARK_CYAN: Int = 0xFF008B8B.toInt()

       @ColorInt
       val AQUA: Int = 0xFF00FFFF.toInt()

       @ColorInt
       val MEDIUM_BLUE: Int = 0xFF0000CD.toInt()

       @ColorInt
       val DARK_BLUE: Int = 0xFF00008B.toInt()

       @ColorInt
       val NAVY: Int = 0xFF000080.toInt()

       @ColorInt
       val BLACK: Int = 0xFF000000.toInt()
   }
}