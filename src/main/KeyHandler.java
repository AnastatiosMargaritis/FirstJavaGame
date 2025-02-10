package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean moveUp, moveDown, moveLeft, moveRight, startDialogue, projectileShoot;
    public boolean enterPressed = false;
    GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // PLAY
        if(gamePanel.gameState == gamePanel.playState){
            playState(code);
        }

        // PAUSE STATE
        if(gamePanel.gameState == gamePanel.pauseState){
           pauseState(code);
        }

        // DIALOGUE STATE
        if(gamePanel.gameState == gamePanel.dialogueState){
            dialogueState(code);
        }

        // CHARACTER STATE
        if(gamePanel.gameState == gamePanel.characterState){
            characterState(code);
        }

        // GAME OVER STATE
        if(gamePanel.gameState == gamePanel.gameOverState){
            gameOverState(code);
        }

        // TRADE STATE
        if(gamePanel.gameState == gamePanel.tradeState){
            tradeState(code);
        }
    }

    public void titleState(int code){

    }

    public void playState(int code){
        if(code == KeyEvent.VK_W){
            moveUp = true;
        }
        if(code == KeyEvent.VK_S){
            moveDown = true;
        }
        if(code == KeyEvent.VK_A){
            moveLeft = true;
        }
        if(code == KeyEvent.VK_D){
            moveRight = true;
        }

        if(code == KeyEvent.VK_P){
            gamePanel.gameState = gamePanel.pauseState;
        }

        if(code == KeyEvent.VK_ENTER){
            startDialogue = true;
        }

        if(code == KeyEvent.VK_C){
            gamePanel.gameState = gamePanel.characterState;
        }

        if(code == KeyEvent.VK_F){
            projectileShoot = true;
        }

    }

    public void pauseState(int code){
        if(code == KeyEvent.VK_ENTER){
            gamePanel.gameState = gamePanel.playState;
        }
    }

    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gamePanel.gameState = gamePanel.playState;
        }
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_ENTER){
            gamePanel.gameState = gamePanel.playState;
        }

        playerInventory(code);

        if (code == KeyEvent.VK_ENTER){
            gamePanel.player.selectItem();
        }

    }

    public void gameOverState(int code){
        if(code == KeyEvent.VK_ENTER){
            if(gamePanel.ui.commandNum == 0){
                gamePanel.gameState = gamePanel.playState;
                gamePanel.retry();
            }
        }
    }

    public void tradeState(int code){
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

       if(gamePanel.ui.substate == 0){
           if(code == KeyEvent.VK_W){
               gamePanel.ui.commandNum--;
               if(gamePanel.ui.commandNum < 0){
                   gamePanel.ui.commandNum = 2;
               }
           }

           if(code == KeyEvent.VK_S){
               gamePanel.ui.commandNum++;
               if(gamePanel.ui.commandNum > 2){
                   gamePanel.ui.commandNum = 0;
               }
           }
       }

       if(gamePanel.ui.substate == 1){
           npcInventory(code);
           if(code == KeyEvent.VK_ESCAPE){
               gamePanel.ui.substate = 0;
           }
       }

        if(gamePanel.ui.substate == 1){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gamePanel.ui.substate = 0;
            }
        }

        if(gamePanel.ui.substate == 2){
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gamePanel.ui.substate = 0;
            }
        }

        if(gamePanel.ui.substate == 2){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gamePanel.ui.substate = 0;
            }
        }
    }

    public void playerInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gamePanel.ui.playerSlotRow != 0){
                gamePanel.ui.playerSlotRow--;
            }

        }

        if(code == KeyEvent.VK_A){
            if(gamePanel.ui.playerSlotCol != 0){
                gamePanel.ui.playerSlotCol--;
            }
        }

        if(code == KeyEvent.VK_S) {
            if(gamePanel.ui.playerSlotRow != 3){
                gamePanel.ui.playerSlotRow++;
            }
        }

        if(code == KeyEvent.VK_D){
            if(gamePanel.ui.playerSlotCol != 4){
                gamePanel.ui.playerSlotCol++;
            }
        }
    }

    public void npcInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gamePanel.ui.npcSlotRow != 0){
                gamePanel.ui.npcSlotRow--;
            }

        }

        if(code == KeyEvent.VK_A){
            if(gamePanel.ui.npcSlotCol != 0){
                gamePanel.ui.npcSlotCol--;
            }
        }

        if(code == KeyEvent.VK_S) {
            if(gamePanel.ui.npcSlotRow != 3){
                gamePanel.ui.npcSlotRow++;
            }
        }

        if(code == KeyEvent.VK_D){
            if(gamePanel.ui.npcSlotCol != 4){
                gamePanel.ui.npcSlotCol++;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            moveUp = false;
        }
        if(code == KeyEvent.VK_S){
            moveDown = false;
        }
        if(code == KeyEvent.VK_A){
            moveLeft = false;
        }
        if(code == KeyEvent.VK_D){
            moveRight = false;
        }

        if(code == KeyEvent.VK_F){
            projectileShoot = false;
        }
    }
}
