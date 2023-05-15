package by.academy.pharmacy2.entity;

import static by.academy.pharmacy2.entity.Constant.AT;
import static by.academy.pharmacy2.entity.Constant.AU;
import static by.academy.pharmacy2.entity.Constant.AUSTRALIA1;
import static by.academy.pharmacy2.entity.Constant.AUSTRIA1;
import static by.academy.pharmacy2.entity.Constant.BE;
import static by.academy.pharmacy2.entity.Constant.BELGIUM1;
import static by.academy.pharmacy2.entity.Constant.CA;
import static by.academy.pharmacy2.entity.Constant.CANADA1;
import static by.academy.pharmacy2.entity.Constant.CROATIA1;
import static by.academy.pharmacy2.entity.Constant.HR;
import static by.academy.pharmacy2.entity.Constant.JAPAN1;
import static by.academy.pharmacy2.entity.Constant.JP;
import static by.academy.pharmacy2.entity.Constant.KR;
import static by.academy.pharmacy2.entity.Constant.PL;
import static by.academy.pharmacy2.entity.Constant.POLAND1;
import static by.academy.pharmacy2.entity.Constant.SE;
import static by.academy.pharmacy2.entity.Constant.SOUTH_KOREA1;
import static by.academy.pharmacy2.entity.Constant.SWEDEN1;
import static by.academy.pharmacy2.entity.Constant.UNITED_STATES1;
import static by.academy.pharmacy2.entity.Constant.US;

public enum Country {
    /**
     * Contains name and ISO code of Australia.
     */
    AUSTRALIA(AUSTRALIA1, AU),
    /**
     * Contains name and ISO code of Austria.
     */
    AUSTRIA(AUSTRIA1, AT),
    /**
     * Contains name and ISO code of Belgium.
     */
    BELGIUM(BELGIUM1, BE),
    /**
     * Contains name and ISO code of Canada.
     */
    CANADA(CANADA1, CA),
    /**
     * Contains name and ISO code of Croatia.
     */
    CROATIA(CROATIA1, HR),
    /**
     * Contains name and ISO code of Japan.
     */
    JAPAN(JAPAN1, JP),
    /**
     * Contains name and ISO code of the United States.
     */
    UNITED_STATES(UNITED_STATES1, US),
    /**
     * Contains name and ISO code of Poland.
     */
    POLAND(POLAND1, PL),
    /**
     * Contains name and ISO code of South Korea.
     */
    SOUTH_KOREA(SOUTH_KOREA1, KR),
    /**
     * Contains name and ISO code of Sweden.
     */
    SWEDEN(SWEDEN1, SE);

    /**
     * Contains name of the country.
     */
    private final String name;
    /**
     * Contains ISO code of the country.
     */
    private final String code;

    Country(final String newName, final String newCode) {
        name = newName;
        code = newCode;
    }

    /**
     * Gets name of the country.
     *
     * @return string value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets ISO code of the country.
     *
     * @return string value of code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns Country enum constant with the specified name.
     *
     * @param newCode country code value.
     * @return Country enum constant.
     */
    public static Country valueOfCode(final String newCode) {
        return switch (newCode) {
            case AU -> AUSTRALIA;
            case AT -> AUSTRIA;
            case BE -> BELGIUM;
            case CA -> CANADA;
            case HR -> CROATIA;
            case JP -> JAPAN;
            case US -> UNITED_STATES;
            case PL -> POLAND;
            case KR -> SOUTH_KOREA;
            case SE -> SWEDEN;
            default -> null;
        };
    }
}
