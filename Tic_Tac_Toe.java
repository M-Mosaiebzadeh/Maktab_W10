
import java.util.ArrayList;
import java.util.Scanner;

public class Tic_Tac_Toe {
    Player player1; // بازیکن 1
    Player player2; // بازیکن 2
    int round = 1; // راند اول
    ArrayList<ArrayList<String>> list; // یک لیست برای ذخیره و نمایش مقادیر
    Scanner scan = new Scanner(System.in);

    // متدی برای ساخت جدول نمایش عددی 1-9
    public void makeTable() {
        for (int i = 0; i < 3; i++) {
            list.add(new ArrayList<>());
            for (int j = (i * 3) + 1; j <= (i * 3) + 3; j++) {
                list.get(i).add(String.valueOf(j));
            }
        }
    }

    // این متد جدول را چاپ میکند(نمایش میدهد)
    public void showTable() {
        for (int i = 0; i < 3; i++) {
            System.out.println(list.get(i));
        }
        System.out.println("****************");
    }

    {
        // پلیر شماره 1 کارکتر خود را انتخاب میکند
        System.out.println("player1 name: ");
        String player1_name = scan.next();
        String player1_symbol;
        // حتما باید بین O و X انتخاب کند
        while (true) {
            System.out.println(player1_name + " choose o or x ");
            player1_symbol = scan.next().toUpperCase();
            if (player1_symbol.equals("O") ||
                    player1_symbol.equals("X")) {
                break;
            } else
                System.out.println("just input o or x ");
        }
        // پلیر دو اسمش را وارد میکند و کارکتر باقی مانده به او تعلق میگیرد
        System.out.println("player2 name: ");
        String player2_name = scan.next();
        String player2_symbol;
        if (player1_symbol.equals("O")) {
            player2_symbol = "X";
        } else
            player2_symbol = "O";

        // پلیر ها ساخته شده و اسم و سمبل خود را میگیرند
        player1 = new Player(player1_name, player1_symbol);
        player2 = new Player(player2_name, player2_symbol);
    }

    // این متد برای این است ک وقتی بازیکن شماره را انتخاب میکند ببیند
    // ایا میتواند سمبل بازیکن را در انجا قرار دهد یا خیر(جایگاه پر نباشد)
    public boolean choose(int number, String symbol) {
        if (number == 0) {
            if (list.get(0).get(0).equals("1")) {
                list.get(0).set(0, symbol);
                return true;
            } else {
                return false;
            }
        }
        int row = number / 3;
        int column = number % 3;

        if (list.get(row).get(column).equals(String.valueOf(number + 1))) {
            list.get(row).set(column, symbol);
            return true;
        }
        return false;
    }

    // این متد شرایط ستونی ، عمودی و قطری را بعد از انتخاب جایگاه توسط کاربر چک میکند
    // که اگر سه تا پشت سر هم باشد true را ارسال میکند
    public boolean checkCondition(int number, String symbol) {
        int column = number % 3;
        int row;
        if (number == 0)
            row = 0;
        else
            row = number / 3;

        int count_Row = 0;
        int count_Column = 0;
        int count_Diameter = 0;

        //check row
        for (int i = 0; i < 3; i++) {
            if (list.get(row).get(i).equals(symbol)) {
                count_Row++;
                if (count_Row == 3)
                    return true;
            }
        }
        // check column
        for (int i = 0; i < 3; i++) {
            if (list.get(i).get(column).equals(symbol)) {
                count_Column++;
                if (count_Column == 3)
                    return true;
            }
        }
        //check diameter
        if (number % 2 == 0) {
            //diameter with rowIndex == columnIndex
            if (row == column) {
                for (int j = 0; j < 3; j++) {
                    if (list.get(j).get(j).equals(symbol)) {
                        count_Diameter++;
                        if (count_Diameter == 3)
                            return true;
                    }
                }
            }
            //diameter with rowIndex != columnIndex or centerIndex
            if (row != column || number == 4) {
                for (int j = 0; j < 3; j++) {
                    if (list.get(j).get(2 - j).equals(symbol)) {
                        count_Diameter++;
                        if (count_Diameter == 3)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    // این متد نوبت هر کاربر را چاپ میکند
    // و شرایط زیر را چک میکند
    public boolean player_move(Player player, int counter) {
        System.out.println(player.name + " «" + player.symbol + "» your turn ");
        while (!scan.hasNextInt()) {
            System.out.println("please input number in range");
            scan.next();
        }
        int digit = scan.nextInt();
        while (!((digit >= 1 && digit <= 9) && (choose(digit - 1, player.symbol)))) {
            System.out.println("please select exist number in range");
            digit = scan.nextInt();
        }
        showTable();

        // اگر کانتر 9 شده باشد یعنی دست تمام شده و بازی مساوی شده
        if (counter == 9) {
            System.out.println("round: " + round + " is draw!");
            round++;
            return true;
        }
        // بعد از اینکه کانتر بالای 4 شد از متد چک میخواهد تا شرایط را چک کند
        if (counter > 4) {
            // اگر شرط true باشد میگوید کاربر با اسم فلان برنده شده است
            if (checkCondition(digit - 1, player1.symbol)) {
                System.out.println(player1.name + " Win round: " + round + " in " + counter + " move");
                player1.point++;
                round++;
                return true;
            }
        }
        return false;
    }

    // طراحی محیط مسابقه
    {
        // نمایش دست چندم بازی
        System.out.println("==================");
        System.out.println("round " + round + " start...");
        System.out.println("==================");
        boolean continuation = true;

        // اگر کاربر بخواهد مسابقه قطع شود این متغیر false میشود
        while (continuation) {

            // در هر دست new  و مقدار دهی اعداد میشود(1-9)
            list = new ArrayList<>();
            makeTable();

            showTable(); // جدول نمایش داده میشود
            int counter = 1;
            // تا زمانی ک هر 9 خانه پر شوند شرط برقرار است مگر کسی برنده شود و حلقه بشکند
            while (counter <= 9) {

                boolean isWin;
                // اگر راند فرد باشد پلیر اول بازی را شروع میکند
                // در غیر اینصورت پلیر دوم بازی را شروع میکند(هر دست یک پلیر بازی را شروع میکند)
                if (round % 2 == 1) {
                    isWin = player_move(player1, counter);
                    if (isWin)
                        break;
                    counter++;
                    isWin = player_move(player2, counter);
                    if (isWin)
                        break;
                    counter++;
                } else {
                    isWin = player_move(player2, counter);
                    if (isWin)
                        break;
                    counter++;
                    isWin = player_move(player1, counter);
                    if (isWin)
                        break;
                    counter++;
                }
            }
            System.out.println("+++++++++++");
            System.out.println("Final point:");
            System.out.println(player1.name + ": " + player1.point +
                    "      " + player2.name + ": " + player2.point);

            // بعد از اتمام بازی از کاربران سوال میشود ایا میخواهند ادامه دهند یا خیر
            while (true) {
                System.out.println("do you want to continue challenge? (y/n)");
                String quit = scan.next();
                if (!(quit.equalsIgnoreCase("y") ||
                        quit.equalsIgnoreCase("n")))
                    System.out.println("enter correct value");
                else {
                    if (quit.equalsIgnoreCase("n")) {
                        continuation = false;
                    }
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Tic_Tac_Toe t = new Tic_Tac_Toe();

    }
}
