package cc.i9mc.gameutils.gui;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GUIAction {
    private int delay;
    private Runnable runnable;
    private boolean close;
}
