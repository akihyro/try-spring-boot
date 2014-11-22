package akihyro.tryspringboot.hoges;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class HogeData {

    /** 数値。 */
    private Integer integer;

    /** 文字列。 */
    private String string;

    /** 文字列リスト。 */
    private List<String> strings;

}
