package com.test.site;

import com.axes.util.jsonx.JsonEachArray;
import com.axes.util.jsonx.Jsonx;
import java.util.HashMap;

public class MBTI {

    private final HashMap<String, String> rp;
    private final HashMap<String, Integer> mapping;
    private String result = "";

    public MBTI(Jsonx values, final String[] ids) throws Exception {
        this.rp = new HashMap<>();
        rp.put("ISTJ", "<ul><li>1.严肃、安静、藉由集中心 志与全力投入、及可被信赖获致成功。</li>"
                + "<li>2.行事务实、有序、实际 、 逻辑、真实及可信赖</li>"
                + "<li>3.十分留意且乐于任何事（工作、居家、生活均有良好组织及有序。</li>"
                + "<li>4.负责任。5.照设定成效来作出决策且不畏阻挠与闲言会坚定为之。</li>"
                + "<li>6.重视传统与忠诚。</li>"
                + "<li>7.传统性的思考者或经理。</li></ul>");
        rp.put("ISFJ", "<ul><li>1.安静、和善、负责任且有良心。</li>"
                + "<li>2.行事尽责投入。</li>"
                + "<li>3.安定性高，常居项目工作或团体之安定力量。</li>"
                + "<li>4.愿投入、吃苦及力求精确。</li>"
                + "<li>5.兴趣通常不在于科技方面。对细节事务有耐心。</li>"
                + "<li>6.忠诚、考虑周到、知性且会关切他人感受。</li>"
                + "<li>7.致力于创构有序及和谐的工作与家庭环境。</li></ul>");
        rp.put("INFJ", "<ul><li>1.因为坚忍、创意及必须达成的意图而能成功。</li>"
                + "<li>2.会在工作中投注最大的努力。</li>"
                + "<li>3.默默强力的、诚挚的及用心的关切他人。</li>"
                + "<li>4.因坚守原则而受敬重。</li>"
                + "<li>5.提出造福大众利益的明确远景而为人所尊敬与追随。</li>"
                + "<li>6.追求创见、关系及物质财物的意义及关联。</li>"
                + "<li>7.想了解什么能激励别人及对他人具洞察力。</li>"
                + "<li>8.光明正大且坚信其价值观。</li>"
                + "<li>9.有组织且果断地履行其愿景。 </li></ul>");
        rp.put("INTJ", "<ul><li>1.具强大动力与本意来达成目的与创意—固执顽固者。</li>"
                + "<li>2.有宏大的愿景且能快速在众多外界事件中找出有意义的模范。</li>"
                + "<li>3.对所承负职务，具良好能力于策划工作并完成。</li>"
                + "<li>4.具怀疑心、挑剔性、独立性、果决，对专业水准及绩效要求高。 </li></ul>");
        rp.put("ISTP", "<ul><li>1.冷静旁观者—安静、预留余地、弹性及会以无偏见的好奇心与未预期原始的幽默观察与分析。</li>"
                + "<li>2.有兴趣于探索原因及效果，技术事件是为何及如何运作且使用逻辑的原理组构事实、重视效能。</li>"
                + "<li>3.擅长于掌握问题核心及找出解决方式。</li>"
                + "<li>4.分析成事的缘由且能实时由大量资料中找出实际问题的核心。</li></ul>");
        rp.put("ISFP", "<ul><li>1.羞怯的、安宁和善地、敏感的、亲切的、且行事谦虚。</li>"
                + "<li>2.喜于避开争论，不对他人强加已见或价值观。</li>"
                + "<li>3.无意于领导却常是忠诚的追随者。</li>"
                + "<li>4.办事不急躁，安于现状无意于以过度的急切或努力破坏现况，且非成果导向。</li>"
                + "<li>5.喜欢有自有的空间及照自订的时程办事。</li>");
        rp.put("INFP", "<ul><li>1安静观察者，具理想性与对其价值观及重要之人具忠诚心。</li>"
                + "<li>2.希外在生活形态与内在价值观相吻合。</li>"
                + "<li>3.具好奇心且很快能看出机会所在。常担负开发创意的触媒者　。</li>"
                + "<li>4.除非价值观受侵犯，行事会具弹性、适应力高且承受力强。</li>"
                + "<li>5.具想了解及发展他人潜能的企图。想作太多且作事全神贯注　。</li>"
                + "<li>6.对所处境遇及拥有不太在意。</li>"
                + "<li>7.具适应力、有弹性除非价值观受到威胁。 </li></ul>");
        rp.put("INTP", "<ul><li>1.安静、自持、弹性及具适应力。</li>"
                + "<li>2.特别喜爱追求理论与科学事理。</li>"
                + "<li>3.习于以逻辑及分析来解决问题—问题解决者。</li>"
                + "<li>4.最有兴趣于创意事务及特定工作，对聚会与闲聊无　大兴趣。</li>"
                + "<li>5.追求可发挥个人强烈兴趣的生涯。</li>"
                + "<li>6.追求发展对有兴趣事务之逻辑解释。</li></ul>");
        rp.put("ESTP", "<ul><li>1.擅长现场实时解决问题—解决问题者。</li>"
                + "<li>2.喜欢办事并乐于其中及过程。</li>"
                + "<li>3.倾向于喜好技术事务及运动，交结同好友人。</li>"
                + "<li>4.具适应性、容忍度、务实性；投注心力于会很快具　成效工作。</li>"
                + "<li>5.不喜欢冗长概念的解释及理论。</li>"
                + "<li>6.最专精于可操作、处理、分解或组合的真实事务。 </li></ul>");
        rp.put("ESFP", "<ul><li>1.外向、和善、接受性、乐于分享喜乐予他人。</li>"
                + "<li>2.喜欢与他人一起行动且促成事件发生，在学习时亦然。</li>"
                + "<li>3.知晓事件未来的发展并会热列参与。</li>"
                + "<li>5.最擅长于人际相处能力及具备完备常识，很有弹性能立即　适应他人与环境。</li>"
                + "<li>6.对生命、人、物质享受的热爱者。 </li></ul>");
        rp.put("ENFP", "<ul><li>1.充满热忱、活力充沛、聪明的、富想象力的，视生命充满机会但期能得自他人肯定与支持。</li>"
                + "<li>2.几乎能达成所有有兴趣的事。</li>"
                + "<li>3.对难题很快就有对策并能对有困难的人施予援手。</li>"
                + "<li>4.依赖能改善的能力而无须预作规划准备。</li>"
                + "<li>5.为达目的常能找出强制自己为之的理由。</li>"
                + "<li>6.即兴执行者。 </li></ul>");
        rp.put("ENTP", "<ul><li>1.反应快、聪明、长于多样事务。</li>"
                + "<li>2.具激励伙伴、敏捷及直言讳专长。</li>"
                + "<li>3.会为了有趣对问题的两面加予争辩。</li>"
                + "<li>4.对解决新及挑战性的问题富有策略，但会轻忽或厌烦经常的任务与细节。</li>"
                + "<li>5.兴趣多元，易倾向于转移至新生的兴趣。</li>"
                + "<li>6.对所想要的会有技巧地找出逻辑的理由。</li>"
                + "<li>7.长于看清础他人，有智能去解决新或有挑战的问题 </li></ul>");
        rp.put("ESTJ", "<ul><li>1.务实、真实、事实倾向，具企业或技术天份。</li>"
                + "<li>2.不喜欢抽象理论；最喜欢学习可立即运用事理。</li>"
                + "<li>3.喜好组织与管理活动且专注以最有效率方式行事以达致成效。</li>"
                + "<li>4.具决断力、关注细节且很快作出决策—优秀行政者。</li>"
                + "<li>5.会忽略他人感受。</li>"
                + "<li>6.喜作领导者或企业主管。 </li></ul>");
        rp.put("ESFJ", "1.诚挚、爱说话、合作性高、受　欢迎、光明正大 的—天生的　合作者及活跃的组织成员。</li>"
                + "<li>2.重和谐且长于创造和谐。</li>"
                + "<li>3.常作对他人有益事务。</li>"
                + "<li>4.给予鼓励及称许会有更佳工作成效。</li>"
                + "<li>5.最有兴趣于会直接及有形影响人们生活的事务。</li>"
                + "<li>6.喜欢与他人共事去精确且准时地完成工作。 </li></ul>");
        rp.put("ENFJ", "<ul><li>1.热忱、易感应及负责任的--具能鼓励他人的领导风格。</li>"
                + "<li>2.对别人所想或希求会表达真正关切且切实用心去处理。</li>"
                + "<li>3.能怡然且技巧性地带领团体讨论或演示文稿提案。</li>"
                + "<li>4.爱交际、受欢迎及富同情心。</li>"
                + "<li>5.对称许及批评很在意。</li>"
                + "<li>6.喜欢带引别人且能使别人或团体发挥潜能。 </li></ul>");
        rp.put("ENTJ", "<ul><li>1.坦诚、具决策力的活动领导者。</li>"
                + "<li>2.长于发展与实施广泛的系统以解决组织的问题。</li>"
                + "<li>3.专精于具内涵与智能的谈话如对公众演讲。</li>"
                + "<li>4.乐于经常吸收新知且能广开信息管道。</li>"
                + "<li>5.易生过度自信，会强于表达自已创见。</li>"
                + "<li>6.喜于长程策划及目标设定 </li></ul>");
        this.mapping = new HashMap<>();
        mapping.put("E", 0);
        mapping.put("S", 0);
        mapping.put("T", 0);
        mapping.put("J", 0);
        mapping.put("I", 0);
        mapping.put("N", 0);
        mapping.put("F", 0);
        mapping.put("P", 0);
        values.each(new JsonEachArray() {
            @Override
            public boolean each(int index, Jsonx json) throws Exception {
                String id = json.get("id").toString();
                boolean has = false;
                for (String k : ids) {
                    if (k.equals(id)) {
                        has = true;
                        break;
                    }
                }
                System.out.println("====>"+id);
                if (has) {
                    json.get("values").each(new JsonEachArray() {
                        @Override
                        public boolean each(int index, Jsonx json) throws Exception {
                            String value = json.get("value").get("value").toString();
                            System.out.println("====<<<"+value);
                            if (mapping.containsKey(value)) {
                                mapping.put(value, mapping.get(value) + 1);
                            }
                            return false;
                        }
                    });
                }
                return false;
            }
        });
        int e = mapping.get("E");
        int i = mapping.get("I");
        int s = mapping.get("S");
        int n = mapping.get("N");
        int t = mapping.get("T");
        int f = mapping.get("F");
        int j = mapping.get("J");
        int p = mapping.get("P");
        if (e <= i) {
            result += "I";
        } else {
            result += "E";
        }
        if (s <= n) {
            result += "N";
        } else {
            result += "S";
        }
        if (t <= f) {
            result += "F";
        } else {
            result += "T";
        }
        if (j <= p) {
            result += "P";
        } else {
            result += "J";
        }
    }

    public String getTableString() {
        String r = "<table style='width:200px'>";
        r += "<tr style='text-align:center;'>";
        r += "<td style='width:50px;'>外向</td>";
        r += "<td>E</td>";
        r += "<td><span style='color:#0078D7'>" + this.mapping.get("E") + "</span></td>";
        r += "<td><span style='color:#0078D7'>" + this.mapping.get("I") + "</span></td>";
        r += "<td>I</td>";
        r += "<td style='width:50px;'>内向</td>";
        r += "</tr>";
        r += "<tr style='text-align:center;'>";
        r += "<td style='width:50px;'>实感</td>";
        r += "<td>S</td>";
        r += "<td><span style='color:#0078D7'>" + this.mapping.get("S") + "</span></td>";
        r += "<td><span style='color:#0078D7'>" + this.mapping.get("N") + "</span></td>";
        r += "<td>N</td>";
        r += "<td style='width:50px;'>直觉</td>";
        r += "</tr>";
        r += "<tr style='text-align:center;'>";
        r += "<td style='width:50px;'>思考</td>";
        r += "<td>T</td>";
        r += "<td><span style='color:#0078D7'>" + this.mapping.get("T") + "</span></td>";
        r += "<td><span style='color:#0078D7'>" + this.mapping.get("F") + "</span></td>";
        r += "<td>F</td>";
        r += "<td style='width:50px;'>情感</td>";
        r += "</tr>";
        r += "<tr style='text-align:center;'>";
        r += "<td style='width:50px;'>判断</td>";
        r += "<td>J</td>";
        r += "<td><span style='color:#0078D7'>" + this.mapping.get("J") + "</span></td>";
        r += "<td><span style='color:#0078D7'>" + this.mapping.get("P") + "</span></td>";
        r += "<td>P</td>";
        r += "<td style='width:50px;'>认知</td>";
        r += "</tr>";
        r += "</table>";
        return r;
    }

    public String getResult() {
        return this.result;
    }

    public String getResultDesc() {
        return this.rp.get(this.result);
    }

    public String getResultString() {
        String r = "";
        r += "<h2>MBTI测试结果：<span style='color:#0078D7'>" + this.result + "<span> </h2>";
        r += "<br/>";
        r += this.getTableString();
        r += "<br/>";
        r+="性格解析：";
        r += "<br/>";
        r += this.getResultDesc();
        return r;
    }
}
