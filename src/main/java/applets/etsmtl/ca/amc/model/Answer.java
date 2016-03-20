
package applets.etsmtl.ca.amc.model;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public class Answer {

    private String answer;
    private String question;
    private String type;
    private String questionId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer The answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The questionId
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId The question_id
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
