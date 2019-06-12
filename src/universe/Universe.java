package universe;

import body.interfaces.Body;

public interface Universe {
    void addBody(Body body);
    void start();
    void stop();
}
