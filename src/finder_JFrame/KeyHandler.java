package finder_JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    DemoPanel dp;

    public KeyHandler(DemoPanel dp){
        this.dp = dp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_ENTER){
            //dp.search();
            dp.autoSearch();
        }
        //if(code == KeyEvent.VK_0){
           // //dp.run();
        //}

    }




    @Override
    public void keyReleased(KeyEvent e) {

    }
}
