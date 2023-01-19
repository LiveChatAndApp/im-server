/*
 * This file is part of the Wildfire Chat package.
 * (c) Heavyrain2012 <heavyrain.lee@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package cn.wildfirechat.server;

public class Server {
    public static void main(String[] args) throws Exception {
        for (String arge : args) {
            System.out.println("arge: "+arge);
        }
        if (args.length > 0 && args[0].equals("0")) {
            io.moquette.server.Server.getServer().stopServer();
            System.exit(-1);
        } else {
            io.moquette.server.Server.start(args);
        }
    }
}
