package com.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel
                implements ActionListener {

        private static final int CELL_SIZE = 40;
        private static final int ROWS = 10;
        private static final int COLS = 10;

        private Board board;
        private Snake snake;
        private Game game;

        private Timer timer;

        private JButton restartButton;

        public GamePanel() {

                setPreferredSize(
                                new Dimension(
                                                COLS * CELL_SIZE,
                                                ROWS * CELL_SIZE));

                setBackground(Color.BLACK);

                setLayout(null);

                setFocusable(true);

                addKeyListener(
                                new MyKeyAdapter());

                restartButton = new JButton("Restart");

                restartButton.setBounds(
                                130,
                                240,
                                120,
                                40);

                restartButton.setVisible(false);

                restartButton.addActionListener(
                                e -> restartGame());

                add(restartButton);

                initializeGame();
        }

        private void initializeGame() {

                board = new Board(ROWS, COLS);

                Cell startCell = board.getCells()[5][5];

                snake = new Snake(startCell);

                game = new Game(snake, board);

                game.setDirection(
                                Game.DIRECTION_RIGHT);

                board.generateFood();

                timer = new Timer(
                                200,
                                this);

                timer.start();

                requestFocusInWindow();
        }

        private void restartGame() {

                timer.stop();

                remove(restartButton);

                restartButton = new JButton("Restart");

                restartButton.setBounds(
                                130,
                                240,
                                120,
                                40);

                restartButton.setVisible(false);

                restartButton.addActionListener(
                                e -> restartGame());

                add(restartButton);

                initializeGame();

                revalidate();

                repaint();
        }

        @Override
        public void actionPerformed(
                        ActionEvent e) {

                if (!game.isGameOver()) {

                        game.update();

                } else {

                        timer.stop();

                        restartButton.setVisible(true);
                }

                repaint();
        }

        @Override
        protected void paintComponent(
                        Graphics g) {

                super.paintComponent(g);
                g.setColor(Color.WHITE);

                g.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                20));

                g.drawString(
                                "Score: " +
                                                (snake.getSnakePartList().size() - 1),
                                10,
                                25);

                drawBoard(g);

                drawFood(g);

                drawSnake(g);

                if (game.isGameOver()) {

                        g.setColor(Color.RED);

                        g.setFont(
                                        new Font(
                                                        "Arial",
                                                        Font.BOLD,
                                                        40));

                        String text = "GAME OVER";

                        FontMetrics metrics = g.getFontMetrics();

                        int x = (getWidth()
                                        - metrics.stringWidth(text))
                                        / 2;

                        g.drawString(
                                        text,
                                        x,
                                        180);

                        g.setFont(
                                        new Font(
                                                        "Arial",
                                                        Font.BOLD,
                                                        20));

                        g.drawString(
                                        "Final Score: "
                                                        + (snake.getSnakePartList().size() - 1),

                                        getWidth() / 2 - 70,
                                        220);
                        restartButton.setVisible(true);
                }
        }

        private void drawBoard(
                        Graphics g) {

                g.setColor(Color.DARK_GRAY);

                for (int i = 0; i <= ROWS; i++) {

                        g.drawLine(
                                        0,
                                        i * CELL_SIZE,
                                        COLS * CELL_SIZE,
                                        i * CELL_SIZE);
                }

                for (int i = 0; i <= COLS; i++) {

                        g.drawLine(
                                        i * CELL_SIZE,
                                        0,
                                        i * CELL_SIZE,
                                        ROWS * CELL_SIZE);
                }
        }

        private void drawSnake(
                        Graphics g) {

                for (Cell cell : snake.getSnakePartList()) {

                        if (cell == snake.getHead()) {

                                g.setColor(
                                                new Color(
                                                                0,
                                                                255,
                                                                0));

                        } else {

                                g.setColor(
                                                new Color(
                                                                0,
                                                                150,
                                                                0));
                        }

                        g.fillRoundRect(
                                        cell.getCol()
                                                        * CELL_SIZE,

                                        cell.getRow()
                                                        * CELL_SIZE,

                                        CELL_SIZE,
                                        CELL_SIZE,

                                        10,
                                        10);

                        if (cell == snake.getHead()) {

                                g.setColor(Color.WHITE);

                                g.fillOval(
                                                cell.getCol()
                                                                * CELL_SIZE + 8,

                                                cell.getRow()
                                                                * CELL_SIZE + 8,

                                                6,
                                                6);

                                g.fillOval(
                                                cell.getCol()
                                                                * CELL_SIZE + 24,

                                                cell.getRow()
                                                                * CELL_SIZE + 8,

                                                6,
                                                6);
                        }
                }
        }

        private void drawFood(
                        Graphics g) {

                Cell[][] cells = board.getCells();

                g.setColor(Color.RED);

                for (int row = 0; row < ROWS; row++) {

                        for (int col = 0; col < COLS; col++) {

                                if (cells[row][col]
                                                .getCellType() == CellType.FOOD) {
                                        int x = col * CELL_SIZE;
                                        int y = row * CELL_SIZE;

                                        g.setColor(Color.RED);

                                        g.fillOval(
                                                        x,
                                                        y,
                                                        CELL_SIZE,
                                                        CELL_SIZE);

                                        g.setColor(Color.GREEN);

                                        g.fillRect(
                                                        x + 18,
                                                        y + 5,
                                                        4,
                                                        8);

                                }
                        }
                }
        }

        private class MyKeyAdapter
                        extends KeyAdapter {

                @Override
                public void keyPressed(
                                KeyEvent e) {

                        switch (e.getKeyCode()) {

                                case KeyEvent.VK_RIGHT:
                                        game.setDirection(
                                                        Game.DIRECTION_RIGHT);
                                        break;

                                case KeyEvent.VK_LEFT:
                                        game.setDirection(
                                                        Game.DIRECTION_LEFT);
                                        break;

                                case KeyEvent.VK_UP:
                                        game.setDirection(
                                                        Game.DIRECTION_UP);
                                        break;

                                case KeyEvent.VK_DOWN:
                                        game.setDirection(
                                                        Game.DIRECTION_DOWN);
                                        break;
                        }
                }
        }
}