package body.surface;

import body.interfaces.Body;
import general_support.Vector;

public interface Surface {
    /**
     *
     * @param body is a body to interact with
     */
    void interactWithBody(Body body);

}
