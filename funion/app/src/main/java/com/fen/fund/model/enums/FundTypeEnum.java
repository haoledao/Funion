package com.fen.fund.model.enums;

public enum FundTypeEnum {

    Item1(0),
    Item2(1),
    Item3(2),
    Item4(3),
    Item5(4),
    Item6(5),
    Item7(6),
    Item8(7),
    Item9(8),
    Item10(9);

    private final int position;

    FundTypeEnum(int pos) {
        position = pos;
    }

    public static FundTypeEnum getType(int position) {
        switch (position) {
            case 0:
                return Item1;
            case 1:
                return Item2;
            case 2:
                return Item3;
            case 3:
                return Item4;
            case 4:
                return Item5;
            case 5:
                return Item6;
            case 6:
                return Item7;
            case 7:
                return Item8;
            case 8:
                return Item9;
            case 9:
                return Item10;
            default:
                return Item1;
        }
    }

    public int getPosition() {
        return position;
    }

}
