package me.study.silang.ui.main.bbs;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Me
 * @since 2019-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private String content;

    private String gmtCreate;

    private String gmtUpdate;

    private String userName;

    private Integer replyNum;
}
