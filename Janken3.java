

import java.io.*;                  
import java.util.*;                    

// ゲームプレイヤーのクラス
class Player
{
   public static final int PAPER   = 0;      // パーの定数定義
   public static final int SCISORS = 1;      // チョキ
   public static final int STONE   = 2;      // グー
   public static final int POINTS_TO_WIN = 3;// ゲーム終了までの勝数
   public static final int WIN[][] = {{PAPER, STONE},   // 勝ちパターンの定義
                                      {SCISORS, PAPER},
                                      {STONE, SCISORS}};
   public static final String HANDS[] = {"パー", "チョキ", "グー"};
                             // ジャンケンの手の定義 0:"パー",1:"キョキ",2:"グー"
   public int point;         // 勝ち点数
   public int choice;        // 選択した手
   public String message;    // 勝利した時のメッセージ

   public Player(String msg) // コンストラクタ
   {
      point = 0;             
      choice = -1;
      message = msg;
   }
  
   boolean victory()     
   {
      return (point == POINTS_TO_WIN);
   }
  
   void fight(HumanPlayer other)  throws IOException   // ジャンケンの実行
   {
    while (!other.victory() && !this.victory()) {
	    
	    System.out.println("あなたの勝数：" + other.point+ "、コンピュータの勝数：" + this.point);
      
      try {
       this.decide();      
	    other.decide();
        System.out.println("あなた：" + HANDS[other.choice]
                           + "、コンピュータ：" + HANDS[this.choice]);
	    judge(other);
      } catch (IOException e) {
  	    throw e;
      }
    }
   }
  
   void victory_speech()    
   {
      if (victory() == true){
        System.out.println(message);
      }
   }
  
   
  
   void decide() throws IOException    // コンピュータの手をランダム生成
   {
      Random rd = new Random();   // 乱数生成用クラスのオブジェクト
	  
      choice = rd.nextInt(3);     // 0～2の整数乱数を返す
   }
  
   void judge(HumanPlayer other)       // 毎回の勝敗を判定
   {
      for(int i=0; i<WIN.length; i++) {
        if (WIN[i][0] == other.choice && WIN[i][1] == this.choice) { // プレーヤの勝ち
           other.point++;
           this.point = 0;
	       break;
        } else if (WIN[i][0] == this.choice && WIN[i][1] == other.choice) {  // コンピュータの勝ち
           this.point++;
           other.point = 0;
	       break;
        }
      }
      if(choice == other.choice){
          System.out.println("ドロー！！");
      }
   }
}

class HumanPlayer extends Player{

    public HumanPlayer(String msg) {
        super(msg);
    }

    @Override
    void decide() throws IOException   
   {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  
      try {
        System.out.println("パーは0、チョキは1、グーは2を入力して下さい！！");
        String str = br.readLine();
        choice = Integer.parseInt(str);
      } catch (IOException e) {
	    throw e;
      }
   }

   void fight(Player other)  throws IOException   // ジャンケンの実行
   {
    while (!other.victory() && !victory()) {
	    
	    System.out.println("あなたの勝数：" + point
                           + "、コンピュータの勝数：" + other.point);
      
      try {
        decide();      
	    other.decide();
        System.out.println("あなた：" + HANDS[choice]
                           + "、コンピュータ：" + HANDS[other.choice]);
	    judge(other);
      } catch (IOException e) {
  	    throw e;
      }
    }
   }

   void judge(Player other)       // 毎回の勝敗を判定
   {
      for(int i=0; i<WIN.length; i++) {
        if (WIN[i][0] == this.choice && WIN[i][1] == other.choice) { // プレーヤの勝ち
           this.point++;
           other.point = 0;
	       break;
        } else if (WIN[i][0] == other.choice && WIN[i][1] == this.choice) {  // コンピュータの勝ち
           other.point++;
           this.point = 0;
	       break;
        }
      }
      if(choice == other.choice){
          System.out.println("ドロー！！");
      }
   }


}

// メインのクラス
class Janken3
{
   public static void main(String args[]) throws IOException
   {
      // 人間プレーヤの生成
      HumanPlayer player = new HumanPlayer("このゲームはあなたの勝ちです！");
      // コンピュータプレーヤの生成
      Player computer = new Player("このゲームはあなたの負けです！"); 
      System.out.println("あなたの勝数：" + player.point
                         + "、コンピュータの勝数：" + computer.point);
      // 勝敗が決するまで繰り返す
      player.fight(computer);
      // computer.fight(player);
    
      computer.victory_speech();
      player.victory_speech();
      return;
   }
}




