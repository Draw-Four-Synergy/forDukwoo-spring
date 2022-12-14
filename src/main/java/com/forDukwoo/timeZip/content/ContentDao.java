package com.forDukwoo.timeZip.content;

import com.forDukwoo.timeZip.content.model.GetContentDetailRes;
import com.forDukwoo.timeZip.content.model.GetContentRes;
import com.forDukwoo.timeZip.content.model.GetEmoticonRes;
import com.forDukwoo.timeZip.user.model.GetUserInfoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ContentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetContentRes> getInterestNews() {
        String getInterestNewsQuery = "select news_id as id, title, view, scrap from news order by view desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getInterestEnNews() {
        String getInterestNewsQuery = "select en_news_id as id, title, view, scrap from en_news order by view desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getInterestAudio() {
        String getInterestNewsQuery = "select audio_id as id, title, view, scrap from audio order by view desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getNews() {
        String getInterestNewsQuery = "select news_id as id, title, view, scrap from news order by news_id desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getEnNews() {
        String getInterestNewsQuery = "select en_news_id as id, title, view, scrap from en_news order by en_news_id desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public List<GetContentRes> getAudio() {
        String getInterestNewsQuery = "select audio_id as id, title, view, scrap from audio order by audio_id desc";
        return this.jdbcTemplate.query(getInterestNewsQuery,
                (rs, rowNum) -> new GetContentRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("view"),
                        rs.getInt("scrap")
                ));
    }

    public GetContentDetailRes getNewsDetail(int id) {
        String getNewsDetailQuery = "select title, content, smile, cry, angry\n" +
                "from news, emoticon\n" +
                "where emoticon.emoticon_id = news.emoticon_id\n" +
                "and news_id = ?";
        long getNewsDetailParam = id;
        return this.jdbcTemplate.queryForObject(getNewsDetailQuery,
                (rs, rowNum) -> new GetContentDetailRes(
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("smile"),
                        rs.getInt("cry"),
                        rs.getInt("angry")
                ), getNewsDetailParam);
    }

    public GetContentDetailRes getEnNewsDetail(int id) {
        String getEnNewsDetailQuery = "select title, content, smile, cry, angry\n" +
                "from en_news, emoticon\n" +
                "where emoticon.emoticon_id = en_news.emoticon_id\n" +
                "and en_news_id = ?";
        long getEnNewsDetailParam = id;
        return this.jdbcTemplate.queryForObject(getEnNewsDetailQuery,
                (rs, rowNum) -> new GetContentDetailRes(
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("smile"),
                        rs.getInt("cry"),
                        rs.getInt("angry")
                ), getEnNewsDetailParam);
    }

    public GetContentDetailRes getAudioDetail(int id) {
        String getAudioDetailQuery = "select title, content, smile, cry, angry\n" +
                "from audio, emoticon\n" +
                "where emoticon.emoticon_id = audio.emoticon_id\n" +
                "and audio_id = ?";
        long getAudioDetailParam = id;
        return this.jdbcTemplate.queryForObject(getAudioDetailQuery,
                (rs, rowNum) -> new GetContentDetailRes(
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("smile"),
                        rs.getInt("cry"),
                        rs.getInt("angry")
                ), getAudioDetailParam);
    }

    public int checkNewsIdExist(int id) {
        String checkContentIdExistQuery = "select exists(select news_id from news where news_id = ?)";
        long checkContentIdExistParams = id;
        return this.jdbcTemplate.queryForObject(checkContentIdExistQuery,
                int.class,
                checkContentIdExistParams);
    }

    public int checkEnNewsIdExist(int id) {
        String checkEnNewsIdExistQuery = "select exists(select en_news_id from en_news where en_news_id = ?)";
        long checkEnNewsIdExistParams = id;
        return this.jdbcTemplate.queryForObject(checkEnNewsIdExistQuery,
                int.class,
                checkEnNewsIdExistParams);
    }

    public int checkAudioIdExist(int id) {
        String checkAudioIdExistQuery = "select exists(select audio_id from audio where audio_id = ?)";
        long checkAudioIdExistParams = id;
        return this.jdbcTemplate.queryForObject(checkAudioIdExistQuery,
                int.class,
                checkAudioIdExistParams);
    }

    public void createScrapNews(int userId, int news_id) {
        String createScrapNewsQuery = "insert into scrap (user_id, news_id) values (?, ?)";
        Object[] createScrapNewsParams = new Object[]{userId, news_id};
        this.jdbcTemplate.update(createScrapNewsQuery, createScrapNewsParams);
    }

    public void createScrapEnNews(int userId, int en_news_id) {
        String createScrapNewsQuery = "insert into scrap (user_id, en_news_id) values (?, ?)";
        Object[] createScrapNewsParams = new Object[]{userId, en_news_id};
        this.jdbcTemplate.update(createScrapNewsQuery, createScrapNewsParams);
    }

    public void createScrapAudio(int userId, int audio_id) {
        String createScrapNewsQuery = "insert into scrap (user_id, audio_id) values (?, ?)";
        Object[] createScrapNewsParams = new Object[]{userId, audio_id};
        this.jdbcTemplate.update(createScrapNewsQuery, createScrapNewsParams);
    }

    public void updateNewsSmile(int id) {
        String updateNewsSmileQuery = "update emoticon set smile = smile + 1 where emoticon_id =\n" +
                "(select emoticon_id from news where news_id = ?)";
        int updateNewsSmileParams = id;
        this.jdbcTemplate.update(updateNewsSmileQuery, updateNewsSmileParams);
    }

    public void updateNewsCry(int id) {
        String updateNewsCryQuery = "update emoticon set cry = cry + 1 where emoticon_id =\n" +
                "(select emoticon_id from news where news_id = ?)";
        int updateNewsCryParams = id;
        this.jdbcTemplate.update(updateNewsCryQuery, updateNewsCryParams);
    }

    public void updateNewsAngry(int id) {
        String updateNewsAngryQuery = "update emoticon set angry = angry + 1 where emoticon_id =\n" +
                "(select emoticon_id from news where news_id = ?)";
        int updateNewsAngryParams = id;
        this.jdbcTemplate.update(updateNewsAngryQuery, updateNewsAngryParams);
    }

    public void updateEnNewsSmile(int id) {
        String updateEnNewsSmileQuery = "update emoticon set smile = smile + 1 where emoticon_id =\n" +
                "(select emoticon_id from en_news where en_news_id = ?)";
        int updateEnNewsSmileParams = id;
        this.jdbcTemplate.update(updateEnNewsSmileQuery, updateEnNewsSmileParams);
    }

    public void updateEnNewsCry(int id) {
        String updateEnNewsCryQuery = "update emoticon set cry = cry + 1 where emoticon_id =\n" +
                "(select emoticon_id from en_news where en_news_id = ?)";
        int updateEnNewsCryParams = id;
        this.jdbcTemplate.update(updateEnNewsCryQuery, updateEnNewsCryParams);
    }

    public void updateEnNewsAngry(int id) {
        String updateEnNewsAngryQuery = "update emoticon set angry = angry + 1 where emoticon_id =\n" +
                "(select emoticon_id from en_news where en_news_id = ?)";
        int updateEnNewsAngryParams = id;
        this.jdbcTemplate.update(updateEnNewsAngryQuery, updateEnNewsAngryParams);
    }

    public void updateAudioSmile(int id) {
        String updateAudioSmileQuery = "update emoticon set smile = smile + 1 where emoticon_id =\n" +
                "(select emoticon_id from audio where audio_id = ?)";
        int updateAudioSmileParams = id;
        this.jdbcTemplate.update(updateAudioSmileQuery, updateAudioSmileParams);
    }

    public void updateAudioCry(int id) {
        String updateAudioCryQuery = "update emoticon set cry = cry + 1 where emoticon_id =\n" +
                "(select emoticon_id from audio where audio_id= ?)";
        int updateAudioCryParams = id;
        this.jdbcTemplate.update(updateAudioCryQuery, updateAudioCryParams);
    }

    public void updateAudioAngry(int id) {
        String updateAudioAngryQuery = "update emoticon set angry = angry + 1 where emoticon_id =\n" +
                "(select emoticon_id from audio where audio_id = ?)";
        int updateAudioAngryParams = id;
        this.jdbcTemplate.update(updateAudioAngryQuery, updateAudioAngryParams);
    }

    public GetEmoticonRes getNewsEmoticon(int id) {
        String GetEmoticonResQuery = "select smile, cry, angry\n" +
                "from emoticon, news\n" +
                "where news.emoticon_id = emoticon.emoticon_id\n" +
                "and news_id = ?";
        long GetEmoticonResParam = id;
        return this.jdbcTemplate.queryForObject(GetEmoticonResQuery,
                (rs, rowNum) -> new GetEmoticonRes(
                        rs.getInt("smile"),
                        rs.getInt("cry"),
                        rs.getInt("angry")
                ), GetEmoticonResParam);
    }

    public GetEmoticonRes getEnNewsEmoticon(int id) {
        String getEnNewsEmoticonQuery = "select smile, cry, angry\n" +
                "from emoticon, en_news\n" +
                "where en_news.emoticon_id = emoticon.emoticon_id\n" +
                "and en_news_id = ?";
        long getEnNewsEmoticonParam = id;
        return this.jdbcTemplate.queryForObject(getEnNewsEmoticonQuery,
                (rs, rowNum) -> new GetEmoticonRes(
                        rs.getInt("smile"),
                        rs.getInt("cry"),
                        rs.getInt("angry")
                ), getEnNewsEmoticonParam);
    }

    public GetEmoticonRes getAudioEmoticon(int id) {
        String getAudioEmoticonQuery = "select smile, cry, angry\n" +
                "from emoticon, audio\n" +
                "where audio.emoticon_id = emoticon.emoticon_id\n" +
                "and audio_id = ?";
        long getAudioEmoticonParam = id;
        return this.jdbcTemplate.queryForObject(getAudioEmoticonQuery,
                (rs, rowNum) -> new GetEmoticonRes(
                        rs.getInt("smile"),
                        rs.getInt("cry"),
                        rs.getInt("angry")
                ), getAudioEmoticonParam);
    }
}
