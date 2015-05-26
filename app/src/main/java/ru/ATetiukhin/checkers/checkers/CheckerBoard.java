package ru.ATetiukhin.checkers.checkers;

import java.util.ArrayList;
import static ru.ATetiukhin.checkers.checkers.CheckerBoard.Piece.*;

public class CheckerBoard {
    public enum Piece {
        LIGHT_CHECKER,
        DARK_CHECKER,
        LIGHT_KING,
        DARK_KING,
        BLANK_FIELD,
        FORBIDDEN_FIELD;

        private static Piece currentMoveCheckers = LIGHT_CHECKER;

        public boolean isCorrectCage() {
            return currentMoveCheckers == castPiece();
        }

        public boolean isOpposite() {
            return this != BLANK_FIELD && this != FORBIDDEN_FIELD &&
                    this.castPiece() != Piece.getCurrentMoveCheckers();
        }

        public static Piece getCurrentMoveCheckers() {
            return currentMoveCheckers;
        }

        public static void initChecker() {
            currentMoveCheckers = LIGHT_CHECKER;
        }

        public static void changeChecker() {
            if (currentMoveCheckers == LIGHT_CHECKER) {
                currentMoveCheckers = DARK_CHECKER;
            } else {
                currentMoveCheckers =LIGHT_CHECKER;
            }
        }

        private Piece castPiece() {
            if (this == LIGHT_CHECKER || this == LIGHT_KING) {
                return LIGHT_CHECKER;
            } else if (this == DARK_CHECKER || this == DARK_KING) {
                return DARK_CHECKER;
            } else {
                return this;
            }
        }
    }

    protected ArrayList<Piece> mDataValues;
    protected int mCountCages = 0;
    protected int mActualCage = 0;
    protected int indexChangeCage = -1;
    public boolean checkerSelected = false;

    public CheckerBoard(int countCages) {
        mCountCages = countCages;
        mDataValues = new ArrayList<Piece>(mCountCages * mCountCages);

        initBoard();
    }

    public Piece getValue(int position) {
        return mDataValues.get(position);
    }

    public int getCountCages() {
        return mCountCages;
    }

    public void checkerSelected(int position) {
        checkerSelected = mDataValues.get(position).isCorrectCage();
        mActualCage = position;
    }

    public void setMovePiece(int position) {
        checkerSelected = false;
        boolean isCaptureChecker = isCaptureChecker(position);
        if (mDataValues.get(position) == BLANK_FIELD && (isMovePiece(position) || isCaptureChecker)) {
            if (isCaptureChecker) {
                mDataValues.set(indexChangeCage, BLANK_FIELD);
            }

            mDataValues.set(mActualCage, BLANK_FIELD);
            mDataValues.set(position, getCurrentMoveCheckers());
            mActualCage = position;

            if (isCaptureChecker && isCheckDoubleCapture()) {
                checkerSelected = true;
            } else {
                changeChecker();
            }
        }
    }


    protected void initBoard() {
        initChecker();
        mDataValues.clear();

        for (int i = 0; i < mCountCages; ++i) {
            for (int j = 0; j < mCountCages; ++j) {
                if ((i + j) % 2 == 0) {
                    mDataValues.add(FORBIDDEN_FIELD);
                } else {
                    if (i < mCountCages / 2 - 1) {
                        mDataValues.add(DARK_CHECKER);
                    } else if (i >= mCountCages / 2 + 1) {
                        mDataValues.add(LIGHT_CHECKER);
                    } else {
                        mDataValues.add(BLANK_FIELD);
                    }
                }
            }
        }
    }

    protected boolean isCaptureChecker(int position) {
        int[] cages = new int [4];

        switch (mDataValues.get(mActualCage)) {
            case LIGHT_CHECKER:
                cages[0] = mActualCage - 2 * mCountCages;
                cages[1] = mActualCage - mCountCages;
                cages[2] = mActualCage + 2 * mCountCages;
                cages[3] = mActualCage + mCountCages;
                return isCapture(cages, position);
            case DARK_CHECKER:
                cages[0] = mActualCage + 2 * mCountCages;
                cages[1] = mActualCage + mCountCages;
                cages[2] = mActualCage - 2 * mCountCages;
                cages[3] = mActualCage - mCountCages;
                return isCapture(cages, position);
            case LIGHT_KING:
                return true;
            case DARK_KING:
                return true;
            default:
                throw new Error();
        }
    }

    protected boolean isCapture(int[] cages,  int position) {
        if (cages[0] - 2 == position && mDataValues.get(cages[1] - 1).isOpposite()) {
            indexChangeCage = cages[1] - 1;
            return true;
        }

        if (cages[0] + 2 == position && mDataValues.get(cages[1] + 1).isOpposite()) {
            indexChangeCage = cages[1] + 1;
            return true;
        }

        if (cages[2] - 2 == position && mDataValues.get(cages[3] - 1).isOpposite()) {
            indexChangeCage = cages[3] - 1;
            return true;
        }

        if (cages[2] + 2 == position && mDataValues.get(cages[3] + 1).isOpposite()) {
            indexChangeCage = cages[3] + 1;
            return true;
        }

        return false;
    }


    protected boolean isMovePiece(int position) {
        int cage;
        switch (mDataValues.get(mActualCage)) {
            case LIGHT_CHECKER:
                cage = mActualCage - mCountCages;
                return cage + 1 == position || cage - 1 == position;
            case DARK_CHECKER:
                cage = mActualCage + mCountCages;
                return cage + 1 == position || cage - 1 == position;
            case LIGHT_KING:
                return true;
            case DARK_KING:
                return true;
            default:
                throw new Error();
        }
    }

    protected boolean isCheckDoubleCapture() {
        int[] cages = new int [4];
        cages[0] = mActualCage - 2 * mCountCages;
        cages[1] = mActualCage - mCountCages;
        cages[2] = mActualCage + 2 * mCountCages;
        cages[3] = mActualCage + mCountCages;

        if (cages[0] - 2 > 0 && cages[0] + 2 < mCountCages * mCountCages
                && cages[1] - 1 > 0 && cages[1] + 1 < mCountCages * mCountCages) {
            if (cages[1] - 1 != indexChangeCage && mDataValues.get(cages[0] - 2) == BLANK_FIELD &&
                    mDataValues.get(cages[1] - 1).isOpposite()) {
                return true;
            }

            if (cages[1] + 1 != indexChangeCage && mDataValues.get(cages[0] + 2) == BLANK_FIELD &&
                mDataValues.get(cages[1] + 1).isOpposite()) {
                return true;
            }
        }

        if (cages[2] - 2 > 0 && cages[2] + 2 < mCountCages * mCountCages
                && cages[3] - 1 > 0 && cages[3] + 1 < mCountCages * mCountCages) {

            if (cages[3] - 1 != indexChangeCage && mDataValues.get(cages[2] - 2) == BLANK_FIELD &&
                    mDataValues.get(cages[3] - 1).isOpposite()) {
                return true;
            }

            if (cages[3] + 1 != indexChangeCage && mDataValues.get(cages[2] + 2) == BLANK_FIELD &&
                    mDataValues.get(cages[3] + 1).isOpposite()) {
                indexChangeCage = cages[2] + 1;
                return true;
            }
        }

        return false;
    }
}
