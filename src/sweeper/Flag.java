package sweeper;

class Flag
{
    private Matrix flagMap;
    private int countOfCloseBoxes;

    void start()
    {
        flagMap = new Matrix(Box.closed);
        countOfCloseBoxes = Ranges.getSize().x*Ranges.getSize().y;
    }

    Box get (Coord coord)
    {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord)
    {
        flagMap.set(coord, Box.opened);
        countOfCloseBoxes--;
    }

    public void setFlagetToBox(Coord coord)
    {
        flagMap.set(coord, Box.flaged);
    }

    void targgleFlagetToBox(Coord coord)
    {
        switch (flagMap.get(coord))
        {
            case flaged:setClosedToBomb(coord); break;
            case closed:setFlagetToBox(coord); break;
        }
    }

    private void setClosedToBomb(Coord coord)
    {
        flagMap.set(coord, Box.closed);
    }

    int getCountCloseBoxes()
    {
        return countOfCloseBoxes;
    }

    void setBombedToBox(Coord coord)
    {
        flagMap.set(coord, Box.bombed);
    }

    public void setOpenedToCloseBombBox(Coord coord1)
    {
        if (flagMap.get(coord1)==Box.closed)
            flagMap.set(coord1, Box.opened);
    }

    public void SetNoBombToFlagedSafeBox(Coord coord1)
    {
        if (flagMap.get(coord1)==Box.flaged)
            flagMap.set(coord1, Box.nobomb);
    }

    public int getCountOfFlaget(Coord coord)
    {
        int count = 0;
        for (Coord coord1: Ranges.getCoordsAround(coord))
            if (flagMap.get(coord1)== Box.flaged)
                count++;
        return count;
    }
}
