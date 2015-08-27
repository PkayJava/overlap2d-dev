/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.o2d.pkayjava.editor.utils.poly.earclipping.bayazit;

// Taken from BayazitDecomposer.cs (FarseerPhysics.Common.Decomposition.BayazitDecomposer)
// at http://farseerphysics.codeplex.com

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.security.InvalidParameterException;

public class SimplifyTools {
    private static Boolean[] _usePt;
    private static double _distanceTolerance;

    // / <summary>
    // / Removes all collinear points on the polygon.
    // / </summary>
    // / <param name="vertices">The polygon that needs simplification.</param>
    // / <param name="collinearityTolerance">The collinearity tolerance.</param>
    // / <returns>A simplified polygon.</returns>
    public static Array<Vector2> CollinearSimplify(Array<Vector2> vertices,
                                                   float collinearityTolerance) {
        // We can't simplify polygons under 3 vertices
        if (vertices.size < 3)
            return vertices;
        Array<Vector2> simplified = new Array<Vector2>();
        for (int i = 0; i < vertices.size; i++) {
            int prevId = i - 1;
            if (prevId < 0)
                prevId = vertices.size - 1;
            int nextId = i + 1;
            if (nextId >= vertices.size)
                nextId = 0;
            Vector2 prev = vertices.get(prevId);
            Vector2 current = vertices.get(i);
            Vector2 next = vertices.get(nextId);
            // If they collinear, continue
            if (Collinear(prev, current, next, collinearityTolerance))
                continue;
            simplified.add(current);
        }
        return simplified;
    }

    public static Boolean Collinear(Vector2 a, Vector2 b, Vector2 c,
                                    float tolerance) {
        return FloatInRange(BayazitDecomposer.Area(a, b, c), -tolerance,
                tolerance);
    }

    public static Boolean FloatInRange(float value, float min, float max) {
        return (value >= min && value <= max);
    }

    // / <summary>
    // / Removes all collinear points on the polygon.
    // / Has a default bias of 0
    // / </summary>
    // / <param name="vertices">The polygon that needs simplification.</param>
    // / <returns>A simplified polygon.</returns>
    public static Array<Vector2> CollinearSimplify(Array<Vector2> vertices) {
        return CollinearSimplify(vertices, 0);
    }

    // / <summary>
    // / Ramer-Douglas-Peucker polygon simplification algorithm. This is the
    // general recursive version that does not use the
    // / speed-up technique by using the Melkman convex hull.
    // /
    // / If you pass in 0, it will remove all collinear points
    // / </summary>
    // / <returns>The simplified polygon</returns>
    public static Array<Vector2> DouglasPeuckerSimplify(
            Array<Vector2> vertices, float distanceTolerance) {
        _distanceTolerance = distanceTolerance;
        _usePt = new Boolean[vertices.size];
        for (int i = 0; i < vertices.size; i++)
            _usePt[i] = true;
        SimplifySection(vertices, 0, vertices.size - 1);
        Array<Vector2> result = new Array<Vector2>();
        for (int i = 0; i < vertices.size; i++)
            if (_usePt[i])
                result.add(vertices.get(i));
        return result;
    }

    private static void SimplifySection(Array<Vector2> vertices, int i, int j) {
        if ((i + 1) == j)
            return;
        Vector2 A = vertices.get(i);
        Vector2 B = vertices.get(j);
        double maxDistance = -1.0;
        int maxIndex = i;
        for (int k = i + 1; k < j; k++) {
            double distance = DistancePointLine(vertices.get(k), A, B);
            if (distance > maxDistance) {
                maxDistance = distance;
                maxIndex = k;
            }
        }
        if (maxDistance <= _distanceTolerance)
            for (int k = i + 1; k < j; k++)
                _usePt[k] = false;
        else {
            SimplifySection(vertices, i, maxIndex);
            SimplifySection(vertices, maxIndex, j);
        }
    }

    private static double DistancePointPoint(Vector2 p, Vector2 p2) {
        double dx = p.x - p2.x;
        double dy = p.y - p2.x;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static double DistancePointLine(Vector2 p, Vector2 A, Vector2 B) {
        // if start == end, then use point-to-point distance
        if (A.x == B.x && A.y == B.y)
            return DistancePointPoint(p, A);
        // otherwise use comp.graphics.algorithms Frequently Asked Questions
        // method
      /*
       * (1) AC dot AB r = --------- ||AB||^2 r has the following meaning: r=0
       * Point = A r=1 Point = B r<0 Point is on the backward extension of AB
       * r>1 Point is on the forward extension of AB 0<r<1 Point is interior
       * to AB
       */
        double r = ((p.x - A.x) * (B.x - A.x) + (p.y - A.y) * (B.y - A.y))
                / ((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
        if (r <= 0.0)
            return DistancePointPoint(p, A);
        if (r >= 1.0)
            return DistancePointPoint(p, B);
      /*
       * (2) (Ay-Cy)(Bx-Ax)-(Ax-Cx)(By-Ay) s = -----------------------------
       * Curve^2 Then the distance from C to Point = |s|*Curve.
       */
        double s = ((A.y - p.y) * (B.x - A.x) - (A.x - p.x) * (B.y - A.y))
                / ((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
        return Math.abs(s)
                * Math.sqrt(((B.x - A.x) * (B.x - A.x) + (B.y - A.y)
                * (B.y - A.y)));
    }

    // From physics2d.net
    public static Array<Vector2> ReduceByArea(Array<Vector2> vertices,
                                              float areaTolerance) {
        if (vertices.size <= 3)
            return vertices;
        if (areaTolerance < 0) {
            throw new InvalidParameterException(
                    "areaTolerance: must be equal to or greater then zero.");
        }
        Array<Vector2> result = new Array<Vector2>();
        Vector2 v1, v2, v3;
        float old1, old2, new1;
        v1 = vertices.get(vertices.size - 2);
        v2 = vertices.get(vertices.size - 1);
        areaTolerance *= 2;
        for (int index = 0; index < vertices.size; ++index, v2 = v3) {
            if (index == vertices.size - 1) {
                if (result.size == 0) {
                    throw new InvalidParameterException(
                            "areaTolerance: The tolerance is too high!");
                }
                v3 = result.get(0);
            } else {
                v3 = vertices.get(index);
            }
            old1 = Cross(v1, v2);
            old2 = Cross(v2, v3);
            new1 = Cross(v1, v3);
            if (Math.abs(new1 - (old1 + old2)) > areaTolerance) {
                result.add(v2);
                v1 = v2;
            }
        }
        return result;
    }

    public static Float Cross(Vector2 a, Vector2 b) {
        return a.x * b.y - a.y * b.x;
    }

    // From Eric Jordan's convex decomposition library
    // / <summary>
    // / Merges all parallel edges in the list of vertices
    // / </summary>
    // / <param name="vertices">The vertices.</param>
    // / <param name="tolerance">The tolerance.</param>
    public static void MergeParallelEdges(Array<Vector2> vertices,
                                          float tolerance) {
        if (vertices.size <= 3)
            return; // Can't do anything useful here to a triangle
        Boolean[] mergeMe = new Boolean[vertices.size];
        int newNVertices = vertices.size;
        // Gather points to process
        for (int i = 0; i < vertices.size; ++i) {
            int lower = (i == 0) ? (vertices.size - 1) : (i - 1);
            int middle = i;
            int upper = (i == vertices.size - 1) ? (0) : (i + 1);
            float dx0 = vertices.get(middle).x - vertices.get(lower).x;
            float dy0 = vertices.get(middle).y - vertices.get(lower).y;
            float dx1 = vertices.get(upper).y - vertices.get(middle).x;
            float dy1 = vertices.get(upper).y - vertices.get(middle).y;
            float norm0 = (float) Math.sqrt(dx0 * dx0 + dy0 * dy0);
            float norm1 = (float) Math.sqrt(dx1 * dx1 + dy1 * dy1);
            if (!(norm0 > 0.0f && norm1 > 0.0f) && newNVertices > 3) {
                // Merge identical points
                mergeMe[i] = true;
                --newNVertices;
            }
            dx0 /= norm0;
            dy0 /= norm0;
            dx1 /= norm1;
            dy1 /= norm1;
            float cross = dx0 * dy1 - dx1 * dy0;
            float dot = dx0 * dx1 + dy0 * dy1;
            if (Math.abs(cross) < tolerance && dot > 0 && newNVertices > 3) {
                mergeMe[i] = true;
                --newNVertices;
            } else
                mergeMe[i] = false;
        }
        if (newNVertices == vertices.size || newNVertices == 0)
            return;
        int currIndex = 0;
        // Copy the vertices to a new list and clear the old
        Array<Vector2> oldVertices = new Array<Vector2>(vertices);
        vertices.clear();
        for (int i = 0; i < oldVertices.size; ++i) {
            if (mergeMe[i] || newNVertices == 0 || currIndex == newNVertices)
                continue;
            // Debug.Assert(currIndex < newNVertices);
            vertices.add(oldVertices.get(i));
            ++currIndex;
        }
    }

    // Misc
    // / <summary>
    // / Merges the identical points in the polygon.
    // / </summary>
    // / <param name="vertices">The vertices.</param>
    // / <returns></returns>
    public static Array<Vector2> MergeIdenticalPoints(Array<Vector2> vertices) {
        Array<Vector2> results = new Array<Vector2>();
        for (int i = 0; i < vertices.size; i++) {
            Vector2 vOriginal = vertices.get(i);

            boolean alreadyExists = false;
            for (int j = 0; j < results.size; j++) {
                Vector2 v = results.get(j);
                if (vOriginal.equals(v)) {
                    alreadyExists = true;
                    break;
                }
            }
            if (!alreadyExists)
                results.add(vertices.get(i));
        }
        return results;
    }

    // / <summary>
    // / Reduces the polygon by distance.
    // / </summary>
    // / <param name="vertices">The vertices.</param>
    // / <param name="distance">The distance between points. Points closer than
    // this will be 'joined'.</param>
    // / <returns></returns>
    public static Array<Vector2> ReduceByDistance(Array<Vector2> vertices,
                                                  float distance) {
        // We can't simplify polygons under 3 vertices
        if (vertices.size < 3)
            return vertices;
        Array<Vector2> simplified = new Array<Vector2>();
        for (int i = 0; i < vertices.size; i++) {
            Vector2 current = vertices.get(i);
            int ii = i + 1;
            if (ii >= vertices.size)
                ii = 0;
            Vector2 next = vertices.get(ii);
            Vector2 diff = new Vector2(next.x - current.x, next.y - current.y);
            // If they are closer than the distance, continue
            if (diff.len2() <= distance)
                continue;
            simplified.add(current);
        }
        return simplified;
    }

    // / <summary>
    // / Reduces the polygon by removing the Nth vertex in the vertices list.
    // / </summary>
    // / <param name="vertices">The vertices.</param>
    // / <param name="nth">The Nth point to remove. Example: 5.</param>
    // / <returns></returns>
    public static Array<Vector2> ReduceByNth(Array<Vector2> vertices, int nth) {
        // We can't simplify polygons under 3 vertices
        if (vertices.size < 3)
            return vertices;
        if (nth == 0)
            return vertices;
        Array<Vector2> result = new Array<Vector2>(vertices.size);
        for (int i = 0; i < vertices.size; i++) {
            if (i % nth == 0)
                continue;
            result.add(vertices.get(i));
        }
        return result;
    }
}
