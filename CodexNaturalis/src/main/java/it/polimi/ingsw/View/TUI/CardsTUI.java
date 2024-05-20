package it.polimi.ingsw.View.TUI;
import java.lang.reflect.Field;
import java.util.HashMap;

public class CardsTUI {
    HashMap<Integer, String> cardsFront=new HashMap<>();
    HashMap<Integer, String> cardsBack=new HashMap<>();

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    //-------------------------------------------resource cards-----------------------------------------------------

    //------------------------------------------------red-----------------------------------------------------------
    String redBack="_________\n"+"|_|              |_|\n"+"|                        |\n"+"|           \uD83C\uDF44           |\n"+"|_                _|\n"+"|_|_____|_|\n";
    String Front1="_________\n"+"|\uD83C\uDF44_|              |_|\n"+"|                        |\n"+"|                        |\n"+"|_                    |\n"+"|\uD83C\uDF44|_______|\n";

    String Front2="_________\n"+"|\uD83C\uDF44_|              |\uD83C\uDF44|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|______|__|\n";

    String Front3="_________\n"+"|_|                   |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83C\uDF44_|_____|\uD83C\uDF44_|\n";

    String Front4="_________\n"+"|                   |\uD83C\uDF44_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_____|\uD83C\uDF44_|\n";

    String Front5="_________\n"+"|                   |\uD83E\uDEB6_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83C\uDF3F_|_____|\uD83C\uDF44_|\n";

    String Front6="_________\n"+"|\uD83E\uDED9_|              |\uD83C\uDF44|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|______|\uD83D\uDC3A|\n";

    String Front7="_________\n"+"|\uD83C\uDF44_|              |\uD83D\uDC3A|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83D\uDCDC_|_____|_|\n";

    String Front8="_________\n"+"|_|       1      |\uD83C\uDF44|\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|_|______|\n";

    String Front9="_________\n"+"|\uD83C\uDF44_|       1           |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_____|_|\n";

    String Front10="_________\n"+"|            1      |_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83C\uDF44_|_____|_|\n";

    //------------------------------------------------green---------------------------------------------------------

    String greenBack="_________\n"+"|_|              |_|\n"+"|                        |\n"+"|           \uD83C\uDF3F           |\n"+"|_                _|\n"+"|_|_____|_|\n";

    String Front11="_________\n"+"|\uD83C\uDF3F_|              |_|\n"+"|                        |\n"+"|                        |\n"+"|_                    |\n"+"|\uD83C\uDF3F|_______|\n";

    String Front12="_________\n"+"|\uD83C\uDF3F_|              |\uD83C\uDF3F|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|______|__|\n";

    String Front13="_________\n"+"|_|                   |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83C\uDF3F_|_____|\uD83C\uDF3F_|\n";

    String Front14="_________\n"+"|                   |\uD83C\uDF3F_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_____|\uD83C\uDF3F_|\n";

    String Front15="_________\n"+"|                   |\uD83E\uDD8B_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83E\uDEB6_|_____|\uD83C\uDF3F_|\n";

    String Front16="_________\n"+"|\uD83C\uDF44_|              |\uD83C\uDF3F|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|______|\uD83E\uDED9|\n";

    String Front17="_________\n"+"|\uD83D\uDCDC_|                   |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83C\uDF3F_|_____|\uD83D\uDC3A_|\n";

    String Front18="_________\n"+"|_|       1      |_|\n"+"|                        |\n"+"|                        |\n"+"|_                    |\n"+"|\uD83C\uDF3F|_______|\n";

    String Front19="_________\n"+"|_|       1      |_|\n"+"|                        |\n"+"|                        |\n"+"|                    __|\n"+"|______|\uD83C\uDF3F|\n";

    String Front20="_________\n"+"|            1      |\uD83C\uDF3F_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_____|_|\n";

    //------------------------------------------------blue----------------------------------------------------------

    String blueBack="_________\n"+"|_|              |_|\n"+"|                        |\n"+"|           \uD83D\uDC3A           |\n"+"|_                _|\n"+"|_|_____|_|\n";

    String Front21="_________\n"+"|\uD83D\uDC3A_|              |\uD83D\uDC3A|\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|_|______|\n";

    String Front22="_________\n"+"|                   |_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83D\uDC3A_|_____|\uD83D\uDC3A_|\n";

    String Front23="_________\n"+"|\uD83D\uDC3A_|                   |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83D\uDC3A_|_____|_|\n";

    String Front24="_________\n"+"|_|              |\uD83D\uDC3A|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|______|\uD83D\uDC3A|\n";

    String Front25="_________\n"+"|                   |\uD83E\uDD8B_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83E\uDED9_|_____|\uD83D\uDC3A_|\n";

    String Front26="_________\n"+"|\uD83C\uDF3F_|              |\uD83D\uDC3A|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|______|\uD83D\uDCDC|\n";

    String Front27="_________\n"+"|\uD83E\uDEB6_|                   |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83D\uDC3A_|_____|\uD83C\uDF44_|\n";

    String Front28="_________\n"+"|            1      |_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83D\uDC3A_|_____|_|\n";

    String Front29="_________\n"+"|_|      1            |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_____|\uD83D\uDC3A_|\n";

    String Front30="_________\n"+"|_|       1      |\uD83D\uDC3A|\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|_|______|\n";

    //------------------------------------------------purple--------------------------------------------------------

    String purpleBack="_________\n"+"|_|              |_|\n"+"|                        |\n"+"|           \uD83E\uDD8B           |\n"+"|_                _|\n"+"|_|_____|_|\n";

    String Front31="_________\n"+"|\uD83E\uDD8B_|              |\uD83E\uDD8B|\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|_|______|\n";

    String Front32="_________\n"+"|                   |_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83E\uDD8B_|_____|\uD83E\uDD8B_|\n";

    String Front33="_________\n"+"|\uD83E\uDD8B_|                   |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83E\uDD8B_|_____|_|\n";

    String Front34="_________\n"+"|_|              |\uD83E\uDD8B|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|______|\uD83E\uDD8B|\n";

    String Front35="_________\n"+"|                   |\uD83E\uDEB6_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83D\uDC3A_|_____|\uD83E\uDD8B_|\n";

    String Front36="_________\n"+"|\uD83D\uDCDC_|              |\uD83E\uDD8B|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|______|\uD83C\uDF44|\n";

    String Front37="_________\n"+"|\uD83E\uDD8B_|              |\uD83C\uDF3F|\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|\uD83E\uDED9|_______|\n";

    String Front38="_________\n"+"|\uD83E\uDD8B_|       1           |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_____|_|\n";

    String Front39="_________\n"+"|_|       1      |_|\n"+"|                        |\n"+"|                        |\n"+"|                    __|\n"+"|______|\uD83E\uDD8B|\n";

    String Front40="_________\n"+"|            1      |\uD83E\uDD8B_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_____|_|\n";

    //------------------------------------------------gold cards----------------------------------------------------

    //---------------------------------------------------red--------------------------------------------------------

    String Front41="_________\n"+"|_|      1\uD83E\uDEB6     |_|\n"+"|                        |\n"+"|                        |\n"+"|_                _|\n"+"|_|_\uD83C\uDF44\uD83C\uDF44\uD83D\uDC3A_|\uD83E\uDEB6|\n";

    String Front42="_________\n"+"|_|      1\uD83E\uDED9     |\uD83E\uDED9|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|___\uD83C\uDF44\uD83C\uDF44\uD83C\uDF3F_|__|\n";

    String Front43="_________\n"+"|\uD83D\uDCDC_|      1\uD83D\uDCDC     |_|\n"+"|                        |\n"+"|                        |\n"+"|_                    |\n"+"|_|_\uD83C\uDF44\uD83C\uDF44\uD83E\uDD8B_____|\n";

    String Front44="_________\n"+"|_|      2\uD83D\uDD32     |_|\n"+"|                        |\n"+"|                        |\n"+"|                    __|\n"+"|__\uD83C\uDF44\uD83C\uDF44\uD83C\uDF44\uD83D\uDC3A_|_|\n";

    String Front45="_________\n"+"|_|      2\uD83D\uDD32     |_|\n"+"|                        |\n"+"|                        |\n"+"|_                    |\n"+"|_|_\uD83C\uDF44\uD83C\uDF44\uD83C\uDF44\uD83C\uDF3F___|\n";

    String Front46="_________\n"+"|_|      2\uD83D\uDD32          |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_\uD83C\uDF44\uD83C\uDF44\uD83C\uDF44\uD83E\uDD8B__|_|\n";

    String Front47="_________\n"+"|_|       3           |\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|\uD83E\uDED9|__\uD83C\uDF44\uD83C\uDF44\uD83C\uDF44___|\n";

    String Front48="_________\n"+"|\uD83E\uDEB6_|       3      |_|\n"+"|                        |\n"+"|                        |\n"+"|                        |\n"+"|___\uD83C\uDF44\uD83C\uDF44\uD83C\uDF44____|\n";

    String Front49="_________\n"+"|            3      |\uD83D\uDCDC_|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|___\uD83C\uDF44\uD83C\uDF44\uD83C\uDF44__|_|\n";

    String Front50="_________\n"+"|_|       5           |\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|_|\uD83C\uDF44\uD83C\uDF44\uD83C\uDF44\uD83C\uDF44\uD83C\uDF44___|\n";

    //------------------------------------------------green---------------------------------------------------------

    String Front51="_________\n"+"|\uD83E\uDEB6_|      1\uD83E\uDEB6     |_|\n"+"|                        |\n"+"|                        |\n"+"|_                    |\n"+"|_|_\uD83C\uDF3F\uD83C\uDF3F\uD83E\uDD8B_____|\n";

    String Front52="_________\n"+"|_|      1\uD83D\uDCDC     |\uD83D\uDCDC|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|___\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF44_|__|\n";

    String Front53="_________\n"+"|_|      1\uD83E\uDED9          |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83E\uDED9_|_\uD83C\uDF3F\uD83C\uDF3F\uD83D\uDC3A_|__|\n";

    String Front54="_________\n"+"|           2\uD83D\uDD32     |_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF3F\uD83E\uDD8B__|_|\n";

    String Front55="_________\n"+"|_|      2\uD83D\uDD32     |_|\n"+"|                        |\n"+"|                        |\n"+"|_                    |\n"+"|_|_\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF3F\uD83D\uDC3A___|\n";

    String Front56="_________\n"+"|_|      2\uD83D\uDD32          |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF44__|_|\n";

    String Front57="_________\n"+"|_|       3           |\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|\uD83E\uDEB6|_\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF3F____|\n";

    String Front58="_________\n"+"|\uD83D\uDCDC_|       3      |_|\n"+"|                        |\n"+"|                        |\n"+"|                        |\n"+"|___\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF3F____|\n";

    String Front59="_________\n"+"|            3      |\uD83E\uDED9_|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|___\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF3F_|__|\n";

    String Front60="_________\n"+"|_|       5      |_|\n"+"|                        |\n"+"|                        |\n"+"|                        |\n"+"|__\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF3F\uD83C\uDF3F____|\n";

    //------------------------------------------------blue----------------------------------------------------------

    String Front61="_________\n"+"|\uD83E\uDED9_|      1\uD83E\uDED9     |_|\n"+"|                        |\n"+"|                        |\n"+"|_                    |\n"+"|_|_\uD83D\uDC3A\uD83D\uDC3A\uD83E\uDD8B_____|\n";

    String Front62="_________\n"+"|           1\uD83D\uDCDC     |_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_\uD83D\uDC3A\uD83D\uDC3A\uD83C\uDF3F_|\uD83D\uDCDC|\n";

    String Front63="_________\n"+"|_|      1\uD83E\uDEB6          |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83E\uDEB6_|_\uD83D\uDC3A\uD83D\uDC3A\uD83C\uDF44_|__|\n";

    String Front64="_________\n"+"|_|      2\uD83D\uDD32     |_|\n"+"|                        |\n"+"|                        |\n"+"|                    __|\n"+"|__\uD83D\uDC3A\uD83D\uDC3A\uD83D\uDC3A\uD83E\uDD8B_|_|\n";

    String Front65="_________\n"+"|_|      2\uD83D\uDD32          |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_\uD83D\uDC3A\uD83D\uDC3A\uD83D\uDC3A\uD83C\uDF44__|_|\n";

    String Front66="_________\n"+"|           2\uD83D\uDD32     |_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_\uD83D\uDC3A\uD83D\uDC3A\uD83D\uDC3A\uD83C\uDF3F__|_|\n";

    String Front67="_________\n"+"|_|       3           |\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|\uD83D\uDCDC|_\uD83D\uDC3A\uD83D\uDC3A\uD83D\uDC3A____|\n";

    String Front68="_________\n"+"|_|       3      |\uD83E\uDED9|\n"+"|                        |\n"+"|                        |\n"+"|                        |\n"+"|___\uD83D\uDC3A\uD83D\uDC3A\uD83D\uDC3A_____|\n";

    String Front69="_________\n"+"|            3      |_|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|___\uD83D\uDC3A\uD83D\uDC3A\uD83D\uDC3A_|\uD83E\uDEB6|\n";

    String Front70="_________\n"+"|            5      |_|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|__\uD83D\uDC3A\uD83D\uDC3A\uD83D\uDC3A\uD83D\uDC3A\uD83D\uDC3A|_|\n";

    //------------------------------------------------purple--------------------------------------------------------
    String Front71="_________\n"+"|_|      1\uD83E\uDEB6     |\uD83E\uDEB6|\n"+"|                        |\n"+"|                        |\n"+"|                    _|\n"+"|___\uD83E\uDD8B\uD83E\uDD8B\uD83C\uDF3F_|__|\n";

    String Front72="_________\n"+"|_|      1\uD83D\uDCDC          |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83D\uDCDC_|_\uD83E\uDD8B\uD83E\uDD8B\uD83D\uDC3A_|__|\n";

    String Front73="_________\n"+"|           1\uD83E\uDED9     |_|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_\uD83E\uDD8B\uD83E\uDD8B\uD83C\uDF44_|\uD83E\uDED9|\n";

    String Front74="_________\n"+"|_|      2\uD83D\uDD32     |_|\n"+"|                        |\n"+"|                        |\n"+"|                    __|\n"+"|__\uD83E\uDD8B\uD83E\uDD8B\uD83E\uDD8B\uD83D\uDC3A_|_|\n";

    String Front75="_________\n"+"|_|      2\uD83D\uDD32     |_|\n"+"|                        |\n"+"|                        |\n"+"|_                    |\n"+"|_|_\uD83E\uDD8B\uD83E\uDD8B\uD83E\uDD8B\uD83C\uDF3F___|\n";

    String Front76="_________\n"+"|_|      2\uD83D\uDD32          |\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|_|_\uD83E\uDD8B\uD83E\uDD8B\uD83E\uDD8B\uD83C\uDF44__|_|\n";

    String Front77="_________\n"+"|\uD83E\uDED9_|       3           |\n"+"|                        |\n"+"|                        |\n"+"|__                    |\n"+"|_|_\uD83E\uDD8B\uD83E\uDD8B\uD83E\uDD8B_____|\n";

    String Front78="_________\n"+"|_|       3      |\uD83D\uDCDC|\n"+"|                        |\n"+"|                        |\n"+"|                        |\n"+"|___\uD83E\uDD8B\uD83E\uDD8B\uD83E\uDD8B_____|\n";

    String Front79="_________\n"+"|            3           |\n"+"|                        |\n"+"|                        |\n"+"|_                _|\n"+"|\uD83E\uDEB6_|_\uD83E\uDD8B\uD83E\uDD8B\uD83E\uDD8B_|__|\n";

    String Front80="_________\n"+"|_|       5      |_|\n"+"|                        |\n"+"|                        |\n"+"|                        |\n"+"|__\uD83E\uDD8B\uD83E\uDD8B\uD83E\uDD8B\uD83E\uDD8B\uD83E\uDD8B____|\n";

    //------------------------------------------------start cards---------------------------------------------------

    String Front81="_________\n"+"|_|              |\uD83C\uDF3F|\n"+"|          __          |\n"+"|          |\uD83E\uDD8B|          |\n"+"|__      ----      _|\n"+"|\uD83E\uDD8B_|_____|_|\n";

    String Front82="_________\n"+"|\uD83D\uDC3A_|              |_|\n"+"|          __          |\n"+"|          |\uD83C\uDF44|          |\n"+"|_      ----      _|\n"+"|_|_____|\uD83C\uDF44_|\n";

    String Front83="_________\n"+"|_|              |_|\n"+"|         ___         |\n"+"|         |\uD83C\uDF3F\uD83C\uDF44|         |\n"+"|__     ------     _|\n"+"|_|_____|_|\n";

    String Front84="_________\n"+"|_|              |_|\n"+"|         ___         |\n"+"|         |\uD83D\uDC3A\uD83E\uDD8B|         |\n"+"|__     ------     _|\n"+"|_|_____|_|\n";

    String Front85="_________\n"+"|_|              |_|\n"+"|        ___        |\n"+"|        |\uD83D\uDC3A\uD83E\uDD8B\uD83C\uDF3F|        |\n"+"|        --------        |\n"+"|________|\n";

    String Front86="_________\n"+"|_|              |_|\n"+"|        ___        |\n"+"|        |\uD83C\uDF3F\uD83D\uDC3A\uD83C\uDF44|        |\n"+"|        --------        |\n"+"|________|\n";

    String Back81="_________\n"+"|\uD83C\uDF44_|              |\uD83C\uDF3F|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83E\uDD8B_|_____|\uD83D\uDC3A_|\n";

    String Back82="_________\n"+"|\uD83C\uDF3F_|              |\uD83D\uDC3A|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83C\uDF44_|_____|\uD83E\uDD8B_|\n";

    String Back83="_________\n"+"|\uD83E\uDD8B_|              |\uD83D\uDC3A|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83C\uDF44_|_____|\uD83C\uDF3F_|\n";

    String Back84="_________\n"+"|\uD83C\uDF3F_|              |\uD83E\uDD8B|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83D\uDC3A_|_____|\uD83C\uDF44_|\n";

    String Back85="_________\n"+"|\uD83E\uDD8B_|              |\uD83C\uDF44|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83C\uDF3F_|_____|\uD83D\uDC3A_|\n";

    String Back86="_________\n"+"|\uD83C\uDF44_|              |\uD83D\uDC3A|\n"+"|                        |\n"+"|                        |\n"+"|__                _|\n"+"|\uD83C\uDF3F_|_____|\uD83E\uDD8B_|\n";

    //------------------------------------------------goal cards----------------------------------------------------

    String Front87="_________\n"+"|           2            |\n"+"|            _           |\n"+"|           _            |\n"+"|          _             |\n"+"|_________|\n";

    String Front88="_________\n"+"|           2            |\n"+"|          _             |\n"+"|           _            |\n"+"|            _           |\n"+"|_________|\n";

    String Front89="_________\n"+"|           2            |\n"+"|            _           |\n"+"|           _            |\n"+"|          _             |\n"+"|_________|\n";

    String Front90="_________\n"+"|           2            |\n"+"|          _             |\n"+"|           _            |\n"+"|            _           |\n"+"|_________|\n";

    String Front91="_________\n"+"|           3            |\n"+"|           _            |\n"+"|           _            |\n"+"|            _           |\n"+"|_________|\n";

    String Front92="_________\n"+"|           3            |\n"+"|           _            |\n"+"|           _            |\n"+"|          _             |\n"+"|_________|\n";

    String Front93="_________\n"+"|           3            |\n"+"|            _           |\n"+"|           _            |\n"+"|           _            |\n"+"|_________|\n";

    String Front94="_________\n"+"|           3            |\n"+"|          _             |\n"+"|           _            |\n"+"|           _            |\n"+"|_________|\n";

    String Front95="_________\n"+"|           2            |\n"+"|                        |\n"+"|           \uD83C\uDF44           |\n"+"|          \uD83C\uDF44\uD83C\uDF44          |\n"+"|_________|\n";

    String Front96="_________\n"+"|           2            |\n"+"|                        |\n"+"|           \uD83C\uDF3F           |\n"+"|          \uD83C\uDF3F\uD83C\uDF3F          |\n"+"|_________|\n";

    String Front97="_________\n"+"|           2            |\n"+"|                        |\n"+"|           \uD83D\uDC3A           |\n"+"|          \uD83D\uDC3A\uD83D\uDC3A          |\n"+"|_________|\n";

    String Front98="_________\n"+"|           2            |\n"+"|                        |\n"+"|           \uD83E\uDD8B           |\n"+"|          \uD83E\uDD8B\uD83E\uDD8B          |\n"+"|_________|\n";

    String Front99="_________\n"+"|           3            |\n"+"|                        |\n"+"|        \uD83E\uDEB6\uD83E\uDED9\uD83D\uDCDC         |\n"+"|                        |\n"+"|_________|\n";

    String Front100="_________\n"+"|           2            |\n"+"|                        |\n"+"|          \uD83D\uDCDC\uD83D\uDCDC          |\n"+"|                        |\n"+"|_________|\n";

    String Front101="_________\n"+"|           2            |\n"+"|                        |\n"+"|          \uD83E\uDED9\uD83E\uDED9          |\n"+"|                        |\n"+"|_________|\n";

    String Front102="_________\n"+"|           2            |\n"+"|                        |\n"+"|          \uD83E\uDEB6\uD83E\uDEB6          |\n"+"|                        |\n"+"|_________|\n";

    public CardsTUI() throws NoSuchFieldException, IllegalAccessException {
        for(int i=1; i<103; i++) {
            Field field = this.getClass().getDeclaredField("Front" + i);
            String cardvalue = (String) field.get(this);
            cardsFront.put(i, cardvalue);
        }
        for(int i=1; i<11; i++){
            cardsBack.put(i,redBack);
        }
        for(int i=41; i<51; i++){
            cardsBack.put(i,redBack);
        }

        for(int i=11; i<21; i++){
            cardsBack.put(i,greenBack);
        }
        for(int i=51; i<61; i++){
            cardsBack.put(i,greenBack);
        }

        for(int i=21; i<31; i++){
            cardsBack.put(i,blueBack);
        }
        for(int i=61; i<71; i++){
            cardsBack.put(i,blueBack);
        }

        for(int i=31; i<41; i++){
            cardsBack.put(i,purpleBack);
        }
        for(int i=71; i<81; i++){
            cardsBack.put(i,purpleBack);
        }
        for(int i=81; i<87; i++){
            Field field = this.getClass().getDeclaredField("Back" + i);
            String cardvalue = (String) field.get(this);
            cardsBack.put(i, cardvalue);
        }
    }
    public String getCard(int cardId) {
        return cardsFront.get(cardId);
    }

    public  String getCardsFront(int i) {
        return cardsFront.get(i);
    }

    public String getCardsBack(int i) {
        return cardsBack.get(i);
    }
}

