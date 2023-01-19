/*
 * This file is part of the Wildfire Chat package.
 * (c) Heavyrain2012 <heavyrain.lee@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package io.moquette.imhandler;

import cn.wildfirechat.common.ErrorCodeView;
import cn.wildfirechat.proto.ProtoConstants;
import cn.wildfirechat.proto.WFCMessage;
import cn.wildfirechat.pojos.GroupNotificationBinaryContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.util.StringUtil;
import io.moquette.spi.impl.Qos1PublishHandler;
import io.netty.buffer.ByteBuf;
import cn.wildfirechat.common.ErrorCode;
import org.springframework.util.StringUtils;
import win.liyufan.im.IMTopic;

import java.util.HashMap;
import java.util.Map;
import static cn.wildfirechat.common.ErrorCode.ERROR_CODE_SUCCESS;

@Handler(value = IMTopic.AddGroupMemberTopic)
public class AddGroupMember extends GroupHandler<WFCMessage.AddGroupMemberRequest> {

    @Override
    public ErrorCode action(ByteBuf ackPayload, String clientID, String fromUser, ProtoConstants.RequestSourceType requestSourceType, WFCMessage.AddGroupMemberRequest request, Qos1PublishHandler.IMCallback callback) {
        boolean isAdmin = requestSourceType == ProtoConstants.RequestSourceType.Request_From_Admin;
        ErrorCodeView errorCodeView = m_messagesStore.addGroupMembers(fromUser, isAdmin, request.getGroupId(), request.getAddedMemberList(), request.getExtra());

        if (errorCodeView.getErrorCode() == ERROR_CODE_SUCCESS) {
            WFCMessage.GroupInfo groupInfo = (WFCMessage.GroupInfo) errorCodeView.getObject();
            Map<String, Object> extraMap = new HashMap<>();
            try {
                if(!StringUtil.isNullOrEmpty(groupInfo.getExtra())) {
                    extraMap = new ObjectMapper().readValue(groupInfo.getExtra(), HashMap.class);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            if (extraMap.get("groupType") != null && !extraMap.get("groupType").equals(2)) {
                if (request.hasNotifyContent() && request.getNotifyContent().getType() > 0) {
                    sendGroupNotification(fromUser, request.getGroupId(), request.getToLineList(), request.getNotifyContent());
                } else {
                    WFCMessage.MessageContent content = new GroupNotificationBinaryContent(request.getGroupId(), fromUser, null, getMemberIdList(request.getAddedMemberList())).getAddGroupNotifyContent();
                    sendGroupNotification(fromUser, request.getGroupId(), request.getToLineList(), content);
                }
            }
        }

        return errorCodeView.getErrorCode();
    }
}
