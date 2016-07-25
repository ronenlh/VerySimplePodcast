/**
 * Copyright 2011, Felix Palmer
 *
 * Licensed under the MIT license:
 * http://creativecommons.org/licenses/MIT/
 */

package com.ronen.studio08.verysimplepodcast.renderer;

/**
 * Created by Ronen on 25/7/16.
 */

// Data class to explicitly indicate that these bytes are raw audio data
public class AudioData
{
    public AudioData(byte[] bytes)
    {
        this.bytes = bytes;
    }

    public byte[] bytes;
}

