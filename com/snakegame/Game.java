package com.snakegame;

public class Game {

    public static final int DIRECTION_NONE = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_LEFT = -1;
    public static final int DIRECTION_UP = 2;
    public static final int DIRECTION_DOWN = -2;

    private Snake snake;
    private Board board;
    private int direction;
    private boolean gameOver;

    public Game(Snake snake, Board board) {
        this.snake = snake;
        this.board = board;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void update() {

        if (!gameOver) {

            if (direction != DIRECTION_NONE) {

                Cell nextCell = getNextCell(
                        snake.getHead());

                if (gameOver) {
                    return;
                }

                boolean foodEaten = nextCell.getCellType() == CellType.FOOD;

                if (snake.checkCrash(nextCell)) {

                    setDirection(
                            DIRECTION_NONE);

                    gameOver = true;

                } else {

                    snake.move(nextCell);

                    if (foodEaten) {

                        snake.grow();

                        board.generateFood();
                    }
                }
            }
        }
    }

    private Cell getNextCell(
            Cell currentPosition) {

        int row = currentPosition.getRow();

        int col = currentPosition.getCol();

        if (direction == DIRECTION_RIGHT) {

            col++;

        } else if (direction == DIRECTION_LEFT) {

            col--;

        } else if (direction == DIRECTION_UP) {

            row--;

        } else if (direction == DIRECTION_DOWN) {

            row++;
        }

        if (row < 0 ||
                row >= board.getCells().length ||
                col < 0 ||
                col >= board.getCells()[0].length) {

            gameOver = true;

            return currentPosition;
        }

        return board
                .getCells()[row][col];
    }
}