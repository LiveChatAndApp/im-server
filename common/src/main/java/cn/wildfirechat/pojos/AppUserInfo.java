package cn.wildfirechat.pojos;

public class AppUserInfo {
    private Long id;// ID
    private String uid;// UID
    private String nickName;// 昵称
    private String memberName;// 帐号
    private Integer accountType;// 帐号类型 1:普通帐号, 2:管理号 [MemberAccountTypeEnum]
    private String inviteCode;// 邀请码
    private Long inviteMemberId;// 邀请用户ID
    private String avatarUrl;// 头像
    private String phone;// 手机号
    private String email;// 邮箱
    private String signature;// 个性签名
    private Integer gender;// 性别 1: 保密, 2: 男, 3: 女 [MemberAccountTypeEnum]
    private Integer loginStatus;// 登陆状态
    private String registerIp;// 注册IP
    private String registerArea;// 注册地区
    private Boolean loginEnable;// 登陆启用
    private Boolean addFriendEnable;// 添加好友开关
    private Boolean createGroupEnable;// 建群开关
    private Boolean adminEnable;// 管理号开关
    private String qrCodeToken;// QRCode验证
    private String memo;// 备注

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getAccountType() {
        return accountType;
    }
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getInviteCode() {
        return inviteCode;
    }
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Long getInviteMemberId() {
        return inviteMemberId;
    }
    public void setInviteMemberId(Long inviteMemberId) {
        this.inviteMemberId = inviteMemberId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getGender() {
        return gender;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }
    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getRegisterIp() {
        return registerIp;
    }
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getRegisterArea() {
        return registerArea;
    }
    public void setRegisterArea(String registerArea) {
        this.registerArea = registerArea;
    }

    public Boolean getLoginEnable() {
        return loginEnable;
    }
    public void setLoginEnable(Boolean loginEnable) {
        this.loginEnable = loginEnable;
    }

    public Boolean getAddFriendEnable() {
        return addFriendEnable;
    }
    public void setAddFriendEnable(Boolean addFriendEnable) {
        this.addFriendEnable = addFriendEnable;
    }

    public Boolean getCreateGroupEnable() {
        return createGroupEnable;
    }
    public void setCreateGroupEnable(Boolean createGroupEnable) {
        this.createGroupEnable = createGroupEnable;
    }

    public Boolean getAdminEnable() {
        return adminEnable;
    }
    public void setAdminEnable(Boolean adminEnable) {
        this.adminEnable = adminEnable;
    }

    public String getQrCodeToken() {
        return qrCodeToken;
    }
    public void setQrCodeToken(String qrCodeToken) {
        this.qrCodeToken = qrCodeToken;
    }

    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
}
