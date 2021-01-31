package sweeper;

public class Game
{
    private Bomb bomb;
    private Flag flag;

    private GameState gstate;
    public GameState getGstate()
    {
        return gstate;
    }

    public Game (int cols, int rows, int bombs)
    {
        Ranges.setSize(new Coord(cols,rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public  void start()
    {
        bomb.start();
        flag.start();
        gstate = GameState.played;
    }

    public Box getbox(Coord coord)
    {
        if (flag.get(coord)==Box.opened)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton(Coord coord)
    {
        //flag.setOpenedToBox(coord);
        if (gameOver())return;
        openBox(coord);
        chekWinner();
    }

    private boolean gameOver()
    {
        if (gstate==GameState.played)
            return false;
        start();
        return true;
    }

    private void chekWinner()
    {
        if (gstate==GameState.played)
            if (flag.getCountCloseBoxes()==bomb.getTotalBomb())
                gstate = GameState.winner;
    }

    private void openBox(Coord coord)
    {
        switch (flag.get(coord))
        {
            case opened: setOpenedToClosedBoxesAroudNumber(coord);return;
            case bomb:return;
            case closed:
                switch (bomb.get(coord))
                {
                    case zero:OpenBoxesAroud(coord);return;
                    case bomb:OpenBomb(coord);return;
                    default:flag.setOpenedToBox(coord); return;
                }
        }
    }

    private void OpenBomb(Coord coord)
    {
        gstate = GameState.bombed;
        flag.setBombedToBox(coord);
        for (Coord coord1:Ranges.getAllCoords())
            if (bomb.get(coord1)==Box.bomb)
                flag.setOpenedToCloseBombBox(coord1);
            else
                flag.SetNoBombToFlagedSafeBox(coord1);
    }

    private void OpenBoxesAroud(Coord coord)
    {
        flag.setOpenedToBox(coord);
        for (Coord coord2:Ranges.getCoordsAround(coord))
        {
            openBox(coord2);
        }
    }

    public void pressRightButton(Coord coord)
    {
        if (gameOver())return;
        flag.targgleFlagetToBox(coord);
    }

    private void setOpenedToClosedBoxesAroudNumber(Coord coord)
    {
        if (bomb.get(coord)!=Box.bomb)
             if (flag.getCountOfFlaget(coord)==bomb.get(coord).getNumber());
                for (Coord coord1 : Ranges.getCoordsAround(coord))
                {
                    if (flag.get(coord)==Box.closed)
                        openBox(coord1);
                }
    }
}
