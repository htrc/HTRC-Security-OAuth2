/*
 *  Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */

package org.wso2.carbon.identity.mgt.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.mgt.ChallengeQuestionProcessor;
import org.wso2.carbon.identity.mgt.IdentityMgtException;
import org.wso2.carbon.identity.mgt.beans.UserMgtBean;
import org.wso2.carbon.identity.mgt.dto.ChallengeQuestionDTO;
import org.wso2.carbon.identity.mgt.dto.UserChallengesDTO;
import org.wso2.carbon.identity.mgt.dto.UserChallengesSetDTO;
import org.wso2.carbon.identity.mgt.internal.IdentityMgtServiceComponent;
import org.wso2.carbon.identity.mgt.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IdentityManagementService Service Class. This exposes identity management APIs for user
 * administrators.
 */
public class IdentityManagementAdminService {

    private static final Log log = LogFactory.getLog(IdentityManagementAdminService.class);

    /**
     * get challenges of user
     *
     * @param userMgtBean  userMgtBean bean class that contains user and tenant Information
     * @return array of challenges  if null, return empty array
     * @throws org.wso2.carbon.identity.mgt.IdentityMgtException  if fails
     */
    public UserChallengesDTO[] getChallengeQuestionsOfUser(UserMgtBean userMgtBean)
                                                                    throws IdentityMgtException {

        Utils.processUserId(userMgtBean);

        ChallengeQuestionProcessor processor = IdentityMgtServiceComponent.
                                                        getRecoveryProcessor().getQuestionProcessor();

        return processor.getChallengeQuestionsOfUser(userMgtBean.getUserId(),
                                                Utils.getTenantId(userMgtBean.getTenantDomain()));
    }

    /**
     * get all promoted user challenges
     *
     * @return array of user challenges
     * @throws IdentityMgtException  if fails
     */
    public UserChallengesSetDTO[] getAllPromotedUserChallenge() throws IdentityMgtException {

        ChallengeQuestionProcessor processor = IdentityMgtServiceComponent.
                                                        getRecoveryProcessor().getQuestionProcessor();
        List<UserChallengesSetDTO> challengeQuestionSetDTOs = new ArrayList<UserChallengesSetDTO>();
        List<ChallengeQuestionDTO> questionDTOs =  processor.getAllChallengeQuestions();
        Map<String, List<UserChallengesDTO>> listMap = new HashMap<String, List<UserChallengesDTO>>();
        for(ChallengeQuestionDTO dto : questionDTOs){

            List<UserChallengesDTO>  dtoList = listMap.get(dto.getQuestionSetId());
            if(dtoList == null){
                dtoList = new ArrayList<UserChallengesDTO>();
            }

            UserChallengesDTO userChallengesDTO = new UserChallengesDTO();
            userChallengesDTO.setId(dto.getQuestionSetId());
            userChallengesDTO.setQuestion(dto.getQuestion());
            userChallengesDTO.setOrder(dto.getOrder());

            dtoList.add(userChallengesDTO);
            listMap.put(dto.getQuestionSetId(), dtoList);
        }

        for(Map.Entry<String, List<UserChallengesDTO>> listEntry : listMap.entrySet()){
            UserChallengesSetDTO dto = new UserChallengesSetDTO();
            dto.setId(listEntry.getKey());
            List<UserChallengesDTO>  dtoList  = listEntry.getValue();
            dto.setChallengesDTOs(dtoList.toArray(new UserChallengesDTO[dtoList.size()]));
            challengeQuestionSetDTOs.add(dto);
        }

        return challengeQuestionSetDTOs.toArray(new UserChallengesSetDTO[challengeQuestionSetDTOs.size()]);
    }

    /**
     * get all challenge questions
     *
     * @return array of questions
     * @throws IdentityMgtException if fails
     */
    public ChallengeQuestionDTO[] getAllChallengeQuestions() throws IdentityMgtException {

        ChallengeQuestionProcessor processor = IdentityMgtServiceComponent.
                                                        getRecoveryProcessor().getQuestionProcessor();
        List<ChallengeQuestionDTO> questionDTOs = processor.getAllChallengeQuestions();
        return questionDTOs.toArray(new ChallengeQuestionDTO[questionDTOs.size()]);

    }

    /**
     * set all challenge questions
     *
     * @param challengeQuestionDTOs  array of questions
     * @throws IdentityMgtException if fails
     */
    public void setChallengeQuestions(ChallengeQuestionDTO[] challengeQuestionDTOs)
                                                                    throws IdentityMgtException {

        ChallengeQuestionProcessor processor = IdentityMgtServiceComponent.
                                                        getRecoveryProcessor().getQuestionProcessor();
        processor.setChallengeQuestions(challengeQuestionDTOs);
    }

    /**
     * set challenges of user
     *
     * @param userMgtBean  userMgtBean bean class that contains user and tenant Information
     * @throws IdentityMgtException  if fails
     */
    public void setChallengeQuestionsOfUser(UserMgtBean userMgtBean) throws IdentityMgtException {


        Utils.processUserId(userMgtBean);

        UserChallengesDTO[] challengesDTOs = userMgtBean.getUserChallenges();

        if(challengesDTOs == null || challengesDTOs.length < 1){
            log.error("no challenges provided by user");
            throw new IdentityMgtException("no challenges provided by user");
        }

        ChallengeQuestionProcessor processor = IdentityMgtServiceComponent.
                                                    getRecoveryProcessor().getQuestionProcessor();

        processor.setChallengesOfUser(userMgtBean.getUserId(), Utils.
                                    getTenantId(userMgtBean.getTenantDomain()), challengesDTOs);
    }
}
