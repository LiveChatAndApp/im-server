/*
 * This file is part of the Wildfire Chat package.
 * (c) Heavyrain2012 <heavyrain.lee@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package com.xiaoleilu.loServer.action.admin;


import cn.wildfirechat.common.APIPath;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.AppUserInfo;
import cn.wildfirechat.pojos.InputCreateGroup;
import cn.wildfirechat.pojos.OutputCreateGroupResult;
import com.xiaoleilu.hutool.lang.Assert;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.FullHttpRequest;
import win.liyufan.im.IMTopic;

@Route(APIPath.Create_Group)
@HttpMethod("POST")
public class CreateGroupAction extends AdminAction {

    @Override
    public boolean isTransactionAction() {
        return true;
    }

    @Override
    public boolean action(Request request, Response response) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            InputCreateGroup inputCreateGroup = getRequestBody(request.getNettyRequest(), InputCreateGroup.class);

            // 呼叫App-Server 检查是否有权限建群
//            String uid = inputCreateGroup.getGroup().getGroup_info().getOwner();
//            AppUserInfo appUserInfo = messagesStore.getAppUserInfo(uid);
//            if(!appUserInfo.getCreateGroupEnable()){
//                setResponseContent(RestResult.resultOf(ErrorCode.ERROR_CODE_USER_CREATE_GROUP_DISABLED), response);
//                return true;
//            }

            if (inputCreateGroup.isValide()) {
                sendApiMessage(response, inputCreateGroup.getOperator(), IMTopic.CreateGroupTopic, inputCreateGroup.toProtoGroupRequest().toByteArray(), result -> {
                    ByteBuf byteBuf = Unpooled.buffer();
                    byteBuf.writeBytes(result);
                    ErrorCode errorCode = ErrorCode.fromCode(byteBuf.readByte());
                    if (errorCode == ErrorCode.ERROR_CODE_SUCCESS) {
                        byte[] data = new byte[byteBuf.readableBytes()];
                        byteBuf.readBytes(data);
                        String groupId = new String(data);
                        return new Result(ErrorCode.ERROR_CODE_SUCCESS, new OutputCreateGroupResult(groupId));
                    } else {
                        return new Result(errorCode);
                    }
                });
                return false;
            } else {
                setResponseContent(RestResult.resultOf(ErrorCode.INVALID_PARAMETER), response);
            }
        }
        return true;
    }
}
