import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame
{
    private Game game;
    private JPanel panel1;
    private JLabel label1;
    private final int cols = 9;
    private final int rows = 9;
    private final int boo = 10;
    private final int img_size = 50;
    public String Prov = "0";

    public static void main(String[] args)
    {
        new JavaSweeper();
    }

        // инициализация всей программы
    private  JavaSweeper()
    {
        game = new Game (cols, rows, boo);
        game.start();
        setImages();
        initLabel();
        initPanel(); // панель
        initFrame(); // окно
    }

    private  void initLabel()
    {
        label1 = new JLabel("Hi!");
        add(label1, BorderLayout.SOUTH);
    }

    private void initPanel() {

        panel1 = new JPanel() {
            @Override // перезапись для графического отображения
            protected void paintComponent (Graphics g) {
                super.paintComponent(g);
                //for (Box box: Box.values())
                for (Coord coord:Ranges.getAllCoords())
                {
                    //Coord coord = new Coord(box.ordinal()*img_size, 0);
                     g.drawImage((Image)game.getbox(coord).image,
                             coord.x*img_size, coord.y*img_size, this);
                 }
            };
        };

        panel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/img_size;
                int y = e.getY()/img_size;
                Coord coord = new Coord(x,y);
                if (e.getButton()==MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton()==MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton()==MouseEvent.BUTTON2)
                    game.start();
                label1.setText(getMessege());
                panel1.repaint();
            }
        });
        // создаём панель определенных размеров
        panel1.setPreferredSize(new Dimension(Ranges.getSize().x * img_size, Ranges.getSize().y * img_size));
        add(panel1); // добавили
    }

    private void initFrame()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // закрытие на крестик
        setTitle("Miner"); // заголовок окна
        setResizable(false); // возможность изменять окно
        setVisible(true); // видимость
        pack(); // окно по размеру внутреннего содержания
        setLocationRelativeTo(null); //место появление окна - центр
        setIconImage(getImage("icon"));
    }

    private void setImages (){
        for (Box box: Box.values() ){
            box.image = getImage(box.name());
        }
    }

    private  Image getImage (String name) //создание метода для вывода картинок
    {
        String fileName = "img/" + name.toLowerCase() + ".png"; // создание ссылки
        ImageIcon icon = new ImageIcon (getClass().getResource(fileName)); // создание картинки
        return  icon.getImage();
    }

    public String getMessege()
    {
        switch (game.getGstate())
        {
            case played: return "Play";
            case bombed: return "BOOM!!!";
            case winner: return "WIN";
            default: return "Hello world!";
        }
    }

}
