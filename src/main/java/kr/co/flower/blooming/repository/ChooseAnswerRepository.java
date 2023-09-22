package kr.co.flower.blooming.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import kr.co.flower.blooming.dto.in.AnswerParam;
import kr.co.flower.blooming.dto.in.ChooseParam;
import lombok.RequiredArgsConstructor;

/**
 * choose & answer table bulk 연산을 하기 위한 repository
 * 
 * @author shmin
 *
 */
@Repository
@RequiredArgsConstructor
public class ChooseAnswerRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * bulk choose insert
     * 
     * @param chooseList
     * @param questionId
     */
    public void bulkSaveChoose(List<ChooseParam> chooseList, long questionId) {
        jdbcTemplate.batchUpdate(
                "insert into choose (choose_seq, choose_content, question_id) values(?,?,?)",
                chooseList, 5,
                (ps, choose) -> {
                    ps.setLong(1, choose.getChooseSeq());
                    ps.setString(2, choose.getChooseContent());
                    ps.setLong(3, questionId);
                });
    }

    /**
     * bulk answer insert
     * 
     * @param answerList
     * @param questionId
     */
    public void bulkSaveAnswer(List<AnswerParam> answerList, long questionId) {
        jdbcTemplate.batchUpdate(
                "insert into answer (answer_content, question_id) values(?,?)",
                answerList, 5,
                (ps, answer) -> {
                    ps.setString(1, answer.getAnswerContent());
                    ps.setLong(2, questionId);
                });
    }


    /**
     * choose update
     * 
     * @param chooseList
     * @param questionId
     */
    public void bulkUpdateChoose(List<ChooseParam> chooseList, long questionId) {
        bulkDeleteChoose(questionId);
        bulkSaveChoose(chooseList, questionId);
    }

    /**
     * answer update
     * 
     * @param answerList
     * @param questionId
     */
    public void bulkUpdateAnswer(List<AnswerParam> answerList, long questionId) {
        bulkDeleteAnswer(questionId);
        bulkSaveAnswer(answerList, questionId);
    }

    /**
     * bulk choose delete
     * 
     * @param questionId
     */
    public void bulkDeleteChoose(long questionId) {
        jdbcTemplate.update("delete from choose where question_id=?", questionId);
    }

    public void bulkDeleteAnswer(long questionId) {
        jdbcTemplate.update("delete from answer where question_id=?", questionId);
    }
}
