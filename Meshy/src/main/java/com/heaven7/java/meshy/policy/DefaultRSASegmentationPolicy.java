/*
 * Copyright 2019
 * heaven7(donshine723@gmail.com)

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.heaven7.java.meshy.policy;

import com.heaven7.java.meshy.SegmentationPolicy;

/**
 * the default rsa segmentation policy.
 * @see SegmentationPolicy
 * @author heaven7
 */
public class DefaultRSASegmentationPolicy implements SegmentationPolicy{

    private int secureSegmentLength;

    public DefaultRSASegmentationPolicy(int secureSegmentLength) {
        this.secureSegmentLength = secureSegmentLength;
    }

    //512 -> 53
    //1024 -> 117
    @Override
    public int getSecureSegmentLength() {
        return secureSegmentLength;
    }
}
