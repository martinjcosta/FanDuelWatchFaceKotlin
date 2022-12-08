/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.wearable.alpha.utils

import android.content.Context
import android.graphics.RectF
import androidx.wear.watchface.BoundingArc
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.complications.ComplicationSlotBounds
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.SystemDataSources
import androidx.wear.watchface.complications.data.ComplicationExperimental
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import com.example.android.wearable.alpha.R

// Information needed for complications.
// Creates bounds for the locations of both right and left complications. (This is the
// location from 0.0 - 1.0.)
// Both left and right complications use the same top and bottom bounds.
private const val LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND = 0.4f
private const val LEFT_AND_RIGHT_COMPLICATIONS_BOTTOM_BOUND = 0.6f

private const val LEFT_COMPLICATION_LEFT_BOUND = 0.2f
private const val LEFT_COMPLICATION_RIGHT_BOUND = 0.4f

private const val RIGHT_COMPLICATION_LEFT_BOUND = 0.6f
private const val RIGHT_COMPLICATION_RIGHT_BOUND = 0.8f

private const val OUTER_COMPLICATION_LEFT_BOUND = 0.4f
private const val OUTER_COMPLICATION_RIGHT_BOUND = 0.6f
private const val OUTER_COMPLICATION_TOP_BOUND = 0.2f
private const val OUTER_COMPLICATION_BOTTOM_BOUND = 0.4f

private const val INNER_COMPLICATION_LEFT_BOUND = 0.4f
private const val INNER_COMPLICATION_RIGHT_BOUND = 0.6f
private const val INNER_COMPLICATION_TOP_BOUND = 0.6f
private const val INNER_COMPLICATION_BOTTOM_BOUND = 0.8f

private const val DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID = R.drawable.complication_red_style

// Unique IDs for each complication. The settings activity that supports allowing users
// to select their complication data provider requires numbers to be >= 0.
internal const val LEFT_COMPLICATION_ID = 100
internal const val RIGHT_COMPLICATION_ID = 101
internal const val OUTER_COMPLICATION_ID = 102
internal const val INNER_COMPLICATION_ID = 103

/**
 * Represents the unique id associated with a complication and the complication types it supports.
 */
sealed class ComplicationConfig(val id: Int, val supportedTypes: List<ComplicationType>) {
    object Left : ComplicationConfig(
        LEFT_COMPLICATION_ID,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )

    object Right : ComplicationConfig(
        RIGHT_COMPLICATION_ID,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )

    object Outer : ComplicationConfig(
        OUTER_COMPLICATION_ID,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )

    object Inner : ComplicationConfig(
        INNER_COMPLICATION_ID,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )
}

// Utility function that initializes default complication slots (left and right).
@OptIn(ComplicationExperimental::class)
fun createComplicationSlotManager(
    context: Context,
    currentUserStyleRepository: CurrentUserStyleRepository,
    drawableId: Int = DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID
): ComplicationSlotsManager {
    val defaultCanvasComplicationFactory =
        CanvasComplicationFactory { watchState, listener ->
            CanvasComplicationDrawable(
                ComplicationDrawable.getDrawable(context, drawableId)!!,
                watchState,
                listener
            )
        }

    val leftComplication = ComplicationSlot.createEdgeComplicationSlotBuilder(
        id = ComplicationConfig.Left.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Left.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_DAY_OF_WEEK,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                LEFT_COMPLICATION_LEFT_BOUND,
                LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND,
                LEFT_COMPLICATION_RIGHT_BOUND,
                LEFT_AND_RIGHT_COMPLICATIONS_BOTTOM_BOUND
            )
        ),
        boundingArc = BoundingArc(
            startAngle = 0.0f,
            totalAngle = 180.0f,
            thickness = 5.0f
        )
    )
        .build()

    val rightComplication = ComplicationSlot.createEdgeComplicationSlotBuilder(
        id = ComplicationConfig.Right.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Right.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_STEP_COUNT,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                RIGHT_COMPLICATION_LEFT_BOUND,
                LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND,
                RIGHT_COMPLICATION_RIGHT_BOUND,
                LEFT_AND_RIGHT_COMPLICATIONS_BOTTOM_BOUND
            )
        ),
        boundingArc = BoundingArc(
            startAngle = 180.0f,
            totalAngle = 180.0f,
            thickness = 5.0f
        )
    ).build()

    val outerComplication = ComplicationSlot.createEdgeComplicationSlotBuilder(
        id = ComplicationConfig.Outer.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Outer.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_STEP_COUNT,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                OUTER_COMPLICATION_LEFT_BOUND,
                OUTER_COMPLICATION_TOP_BOUND,
                OUTER_COMPLICATION_RIGHT_BOUND,
                OUTER_COMPLICATION_BOTTOM_BOUND
            )
        ),
        boundingArc = BoundingArc(
            startAngle = 0f,
            totalAngle = 360f,
            thickness = 0.1f
        )
    ).build()

    val innerComplication = ComplicationSlot.createEdgeComplicationSlotBuilder(
        id = ComplicationConfig.Inner.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Inner.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_STEP_COUNT,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                INNER_COMPLICATION_LEFT_BOUND,
                INNER_COMPLICATION_TOP_BOUND,
                INNER_COMPLICATION_RIGHT_BOUND,
                INNER_COMPLICATION_BOTTOM_BOUND
            )
        ),
        boundingArc = BoundingArc(
            startAngle = 0f,
            totalAngle = 360f,
            thickness = 0.1f
        )
    ).build()


    return ComplicationSlotsManager(
        listOf(leftComplication, rightComplication),
        currentUserStyleRepository
    )

}
