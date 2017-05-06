<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper  namespace="com.jd.ipc.wil.dao.rule.RuleCategoryLimitMapper">

    <sql id="Base_Column_List">
        id, first_category_id, first_category_name, second_category_id, second_category_name,
        third_category_id, third_category_name, dc_id,  store_type, store_id,
        creator_erp, updater_erp,   create_time, update_time
    </sql>


${r'

    <sql id="paramToSql">
        <where>

            <if test="id != null">
                and id= #{id}
            </if>

            <if test="dcId != null">
                and dc_id=#{dcId}
            </if>

        </where>
    </sql>



    <sql id="limitToSql">
        <if test="start!=null and start >=0 and length!=null and length>0">
            limit #{start},#{length}
        </if>
    </sql>

    <select id="queryRuleCategoryLimitList" resultType="RuleLimit"   >
        select  <include refid="Base_Column_List" />
        from wil_rule_category_limit
        <include refid="paramToSql"></include>
        order by update_time desc
        <include refid="limitToSql"></include>

    </select>

    <select id="selectRuleCategoryLimitCount" resultType="int">
        select count(1) from wil_rule_category_limit
        <include refid="paramToSql"></include>
    </select>





    <insert id="insertRuleCategoryLimit" parameterType="RuleLimitParams">
        insert into wil_rule_category_limit ( first_category_id, first_category_name,
          second_category_id, second_category_name, third_category_id,   third_category_name,
          dc_id,store_type, store_id,
          creator_erp, updater_erp, create_time, update_time
          )
        values (  #{firstCategoryId}, #{firstCategoryName},
          #{secondCategoryId}, #{secondCategoryName}, #{thirdCategoryId}, #{thirdCategoryName},
           #{dcId},  #{storeType}, #{storeId},
          #{creatorErp}, #{updaterErp},  #{createTime}, #{updateTime}
          )
    </insert>


    <insert id="batchInsertRuleCategoryLimit">
        insert into wil_rule_category_limit ( first_category_id, first_category_name,
        second_category_id, second_category_name, third_category_id,
        third_category_name, dc_id,store_type, store_id,
        creator_erp,   updater_erp,     create_time, update_time
        )
        values
        <foreach collection="list" item="bean" separator="),(" open="(" close=")" >

            #{bean.firstCategoryId}, #{bean.firstCategoryName},
            #{bean.secondCategoryId}, #{bean.secondCategoryName}, #{bean.thirdCategoryId},
            #{bean.thirdCategoryName}, #{bean.dcId}, #{bean.storeType}, #{bean.storeId},
            #{bean.creatorErp},  #{bean.updaterErp}, #{bean.createTime}, #{bean.updateTime}

        </foreach>

    </insert>



    <update id="updateRuleCategoryLimit" parameterType="RuleLimitParams" >

            update wil_rule_category_limit
            set
                  first_category_id = #{firstCategoryId},
                  first_category_name = #{firstCategoryName},
                  second_category_id = #{secondCategoryId},
                  second_category_name = #{secondCategoryName},
                  third_category_id = #{thirdCategoryId},
                  third_category_name = #{thirdCategoryName},
                  dc_id = #{dcId},
                  store_type = #{storeType},
                  store_id = #{storeId},

                  updater_erp = #{updaterErp},
                  update_time = #{updateTime}

            where id=#{id}


    </update>


    <delete id="deleteRuleCategoryLimit" parameterType="RuleLimitParams" >
        DELETE FROM wil_rule_category_limit
        <where>

            id IN
            <foreach item="item" index="index" collection="ids"
                     open="(" separator="," close=")">
                #{item}
            </foreach>

            <if test="coverageSubStore != null   ">

                <if test="firstCategoryId != null ">
                    and first_category_id=#{firstCategoryId}
                </if>

                <if test="secondCategoryId != null  ">
                    and second_category_id=#{secondCategoryId}
                </if>

                <if test="thirdCategoryId != null   ">
                    and third_category_id=#{thirdCategoryId}
                </if>


                <if test="dcId != null">
                    and dc_id=#{dcId}
                </if>


                and store_id != -1

            </if>

        </where>

    </delete>

 '}


</mapper>