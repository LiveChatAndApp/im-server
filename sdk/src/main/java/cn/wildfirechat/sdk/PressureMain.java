package cn.wildfirechat.sdk;

import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.messagecontentbuilder.TextMessageContentBuilder;
import cn.wildfirechat.pojos.*;
import cn.wildfirechat.proto.ProtoConstants;
import cn.wildfirechat.sdk.model.IMResult;
import cn.wildfirechat.sdk.paige.NettyClient;

import java.util.ArrayList;
import java.util.List;


public class PressureMain {
    private static boolean commercialServer = false;
    private static boolean advanceVoip = false;
    //管理端口是8080
    private static String AdminUrl =     "http://192.168.1.32:18080";
    private static String AdminSecret = "123456";

    private static String IMUrl = "http://localhost";

    private static String MEMBER_SENDER = "paigeSender";
    private static Integer MEMBER_COUNT = 1000;
    private static String MEMBER_PREFIX = "paige";
    private static Long MEMBER_MOBILE_START = 13900000001L;

    private static String gid = "99gqmws2k";

    public static void main(String[] args) throws Exception {
        AdminConfig.initAdmin(AdminUrl, AdminSecret);

//        new Thread(()->{
//            while (true) {
//                try {
//                    connect();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();

        activationSender();

        List<String> users = new ArrayList<>();
        String senderId = String.format("%s%s", MEMBER_PREFIX, "Id");
        Long mobileIndex = MEMBER_MOBILE_START;
        for(int i=1;i<=MEMBER_COUNT;i++) {
            InputOutputUserInfo userInfo = new InputOutputUserInfo();
            userInfo.setUserId(String.format("%s%s%s", MEMBER_PREFIX, "Id", i));
            userInfo.setName(String.format("%s%s%s", MEMBER_PREFIX, "Name", i));
            userInfo.setMobile(mobileIndex.toString());
            userInfo.setDisplayName(String.format("%s%s%s", MEMBER_PREFIX, "DisplayName", i));
            if (!isMemberExist(userInfo.getUserId())) {
                createMember(userInfo);
            }
//            kickoffUserClient(userInfo.getUserId(), null);
//            kickoffUserClient(userInfo.getUserId(), UUID.randomUUID().toString());
            sendMessage(senderId, userInfo.getUserId(),"Hello");
            users.add(userInfo.getUserId());
            mobileIndex++;
//            Thread.sleep(1000);
        }

        users.parallelStream().forEach(user -> {
            try {
                sendMessage(senderId, user,"Hello");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        kickoffGroupMembers(users);
        addGroupMembers(users);
        sendGroupMessage();
    }

    private static boolean isMemberExist(String memberId) throws Exception {
        InputOutputUserInfo userInfo = getMember(memberId);
        return userInfo != null && userInfo.getUserId() != null ? true : false;
    }

    private static void getMemberToken(String memberId, String clientId) throws Exception {
        String logPrefix = "【获取用户Token】";
        IMResult<OutputGetIMTokenData> imResult = UserAdmin.getUserToken(memberId, clientId, ProtoConstants.Platform.Platform_Android);
        if (imResult != null && imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
            System.out.println(logPrefix+" memberId: "+memberId+", clientId: "+clientId+" SUCCESS");
        } else {
            System.out.println(logPrefix+" memberId: "+memberId+", clientId: "+clientId+" FAILURE, code: "+imResult.getCode()+" msg: "+imResult.getMsg());
        }
    }

    private static void connect() throws Exception {
        String host = "192.168.1.32";
        int port = 1883;
//        NettyServer server = new NettyServer(port);
//        server.run();
        Thread.sleep(1000);
        NettyClient client = new NettyClient(host, port);
        client.connect();
    }

    private static void kickoffUserClient(String memberId, String clientId) throws Exception {
        String logPrefix = "【强迫用户下线】";
        IMResult<Void> imResult = UserAdmin.kickoffUserClient(memberId, clientId);
        if (imResult.getCode() == ErrorCode.ERROR_CODE_SUCCESS.getCode()) {
            System.out.println(logPrefix+" memberId: "+memberId+", clientId: "+clientId+" SUCCESS");
        } else {
            System.out.println(logPrefix+" memberId: "+memberId+", clientId: "+clientId+" FAILURE, code: "+imResult.getCode()+" msg: "+imResult.getMsg());
        }
    }

    private static void sendMessage(String sender, String receive, String message) throws Exception {
        String logPrefix = "【发送讯息】";
        Conversation conversation = new Conversation();
        conversation.setTarget(receive);
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = TextMessageContentBuilder.newBuilder(message + " "+receive).build();

        IMResult<SendMessageResult> imResult = MessageAdmin.sendMessage(sender, conversation, payload, null);
        if (imResult != null && imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
            System.out.println(logPrefix+" sender: "+sender+", receive: "+receive+" SUCCESS");
        } else {
            System.out.println(logPrefix+" sender: "+sender+", receive: "+receive+" FAILURE, code: "+imResult.getCode()+" msg: "+imResult.getMsg());
        }
    }

    private static InputOutputUserInfo getMember(String memberId) throws Exception {
        String logPrefix = "【查询会员】";
        InputOutputUserInfo result = null;
        IMResult<InputOutputUserInfo> imResult = UserAdmin.getUserByUserId(memberId);
        if (imResult != null && imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
            System.out.println(logPrefix+" memberId: "+memberId+" SUCCESS");
            result = imResult.getResult();
        } else {
            System.out.println(logPrefix+" memberId: "+memberId+" FAILURE, code: "+imResult.getCode()+" msg: "+imResult.getMsg());
        }
        return result;
    }

    private static void createMember(InputOutputUserInfo userInfo) throws Exception {
        String logPrefix = "【创建会员】";
        IMResult<OutputCreateUser> resultCreateUser = UserAdmin.createUser(userInfo);
        if (resultCreateUser != null && resultCreateUser.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
            System.out.println(logPrefix+" userInfo: "+userInfo+" SUCCESS");
        } else {
            System.out.println(logPrefix+" userInfo: "+userInfo+" FAILURE");
            System.exit(-1);
        }
    }

    private static void activationSender() throws Exception {
        InputOutputUserInfo userInfo = new InputOutputUserInfo();
        userInfo.setUserId(String.format("%s%s", MEMBER_PREFIX, "Id"));
        userInfo.setName(String.format("%s%s", MEMBER_PREFIX, "Name"));
        userInfo.setMobile("13900000000");
        userInfo.setDisplayName(String.format("%s%s", MEMBER_PREFIX, "DisplayName"));
        if (!isMemberExist(userInfo.getUserId())) {
            createMember(userInfo);
        }
//        kickoffUserClient(userInfo.getUserId(), UUID.randomUUID().toString());
    }

    private static void kickoffGroupMembers(List<String> users) throws Exception {
        String logPrefix = "【剔除群组会员】";
        String senderId = String.format("%s%s", MEMBER_PREFIX, "Id");
        IMResult<Void> imResult = GroupAdmin.kickoffGroupMembers(senderId, gid, users, null, null);
        if (imResult != null && imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
            System.out.println(logPrefix+" gid: "+gid+", users size: "+users.size()+" SUCCESS");
        } else {
            System.out.println(logPrefix+" gid: "+gid+", users size: "+users.size()+" FAILURE, code: "+imResult.getCode()+" msg: "+imResult.getMsg());
        }
    }

    private static void addGroupMembers(List<String> users) throws Exception {
        String logPrefix = "【加入群组会员】";
        String senderId = String.format("%s%s", MEMBER_PREFIX, "Id");

        List<PojoGroupMember> pojoGroupMembers = new ArrayList<>();
        users.forEach(user -> {
            PojoGroupMember groupMember = new PojoGroupMember();
            groupMember.setMember_id(user);
            groupMember.setAlias("hello "+user);
            pojoGroupMembers.add(groupMember);
        });

        IMResult<Void> imResult = GroupAdmin.addGroupMembers(senderId, gid, pojoGroupMembers, null, null);
        if (imResult != null && imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
            System.out.println(logPrefix+" gid: "+gid+", users size: "+users.size()+" SUCCESS");
        } else {
            System.out.println(logPrefix+" gid: "+gid+", users size: "+users.size()+" FAILURE, code: "+imResult.getCode()+" msg: "+imResult.getMsg());
        }
    }

    private static void sendGroupMessage() throws Exception {
        String logPrefix = "【群组发信息】";
        String senderId = String.format("%s%s", MEMBER_PREFIX, "Id");
        Conversation conversation = new Conversation();
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Group);
        conversation.setTarget(gid);

        MessagePayload payload = new MessagePayload();
        payload.setType(1);
        payload.setSearchableContent("hello");
        payload.setMediaType(0);
        payload.setPushContent("派奇请求");
        payload.setPushData("");
        payload.setMentionedType(0);
        IMResult<SendMessageResult> imResult = MessageAdmin.sendMessage(senderId, conversation, payload);
        if (imResult.getCode() == ErrorCode.ERROR_CODE_SUCCESS.getCode()) {
            System.out.println(logPrefix+" gid: "+gid+" SUCCESS");
        } else {
            System.out.println(logPrefix+" gid: "+gid+" FAILURE, code: "+imResult.getCode()+" msg: "+imResult.getMsg());
        }
    }
}
