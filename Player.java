
public class Player {
    String name; // متغیر نام برای هر بازیکن
    String symbol; // متغیر علامت هر بازیکن
    int point = 0; // امتیاز هر بازیکن که ابتدا صفر است

    // کانستراکتور بازیکن
    Player(String name,String symbol){
        this.name = name;
        this.symbol = symbol;
    }
}
