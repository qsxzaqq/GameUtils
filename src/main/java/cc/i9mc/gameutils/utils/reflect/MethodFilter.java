package cc.i9mc.gameutils.utils.reflect;

import java.lang.reflect.Method;

/**
 * @author Zoyn
 * @since 2017-12-02
 */
public interface MethodFilter {

    boolean accept(Method method);

}
