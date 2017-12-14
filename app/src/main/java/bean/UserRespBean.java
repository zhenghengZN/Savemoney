package bean;

/**
 * 用户信息bean
 * Created by wneng on 16/7/28.
 */

public class UserRespBean {

    private String id;

    private String username;

    private String nickname;

    private String displayName;

    private String profileUrl;

    private String personalSigniture;

    private int points;

    private int postCount;

    private int followeeCount;

    private int followerCount;
    private String isOfficial;
    private int talentLevel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFolloweeCount() {
        return followeeCount;
    }

    public void setFolloweeCount(int followeeCount) {
        this.followeeCount = followeeCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPersonalSigniture() {
        return personalSigniture;
    }

    public void setPersonalSigniture(String personalSigniture) {
        this.personalSigniture = personalSigniture;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    public int getTalentLevel() {
        return talentLevel;
    }

    public void setTalentLevel(int talentLevel) {
        this.talentLevel = talentLevel;
    }
}
