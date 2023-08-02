/* BlankFactor (C)2023 */
package com.marqeta.api.fundingsources;

import com.marqeta.api.commons.CommonAssertions;
import com.marqeta.api.commons.CommonRequests;

public class ProgramGatewayRequests {

    private static final String SERVICE_PATH = "marqeta.funding.endpoint";
    private static final String SERVICE_PAYLOAD = "marqeta.funding.payload";
    private static final String RESPONSE_SCHEMA = "marqeta.funding.schema";

    private ProgramGatewayRequests() {
        // Private constructor to prevent instantiation
    }

    public static String createFundingSource(CommonRequests commonRequests) {
        commonRequests.post(SERVICE_PATH, SERVICE_PAYLOAD);
        CommonAssertions.verifyFullCreatedResponseAndSchema(RESPONSE_SCHEMA);
        return CommonAssertions.validateIfTheTokenIsAGuidAndGetIt(ProgramGatewayResponse.TOKEN);
    }
}
