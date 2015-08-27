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

import java.security.InvalidParameterException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/// <summary>
/// Convex decomposition algorithm created by Mark Bayazit (http://mnbayazit.com/)
/// For more information about this algorithm, see http://mnbayazit.com/406/bayazit
/// </summary>
public class BayazitDecomposer {

   public static final float Epsilon = 1.192092896e-07f;
   public static int MaxPolygonVertices = 8;

   public static Vector2 Cross(Vector2 a, float s) {
      return new Vector2(s * a.y, -s * a.x);
   }

   private static Vector2 At(int i, Array<Vector2> vertices) {
      int s = vertices.size;
      return vertices.get(i < 0 ? s - (-i % s) : i % s);
   }

   private static Array<Vector2> Copy(int i, int j, Array<Vector2> vertices) {
      Array<Vector2> p = new Array<Vector2>();
      while (j < i)
         j += vertices.size;
      // p.reserve(j - i + 1);
      for (; i <= j; ++i) {
         p.add(At(i, vertices));
      }
      return p;
   }

   public static float GetSignedArea(Array<Vector2> vect) {
      int i;
      float area = 0;
      for (i = 0; i < vect.size; i++) {
         int j = (i + 1) % vect.size;
         area += vect.get(i).x * vect.get(j).y;
         area -= vect.get(i).y * vect.get(j).x;
      }
      area /= 2.0f;
      return area;
   }

   public static float GetSignedArea(Vector2[] vect) {
      int i;
      float area = 0;
      for (i = 0; i < vect.length; i++) {
         int j = (i + 1) % vect.length;
         area += vect[i].x * vect[j].y;
         area -= vect[i].y * vect[j].x;
      }
      area /= 2.0f;
      return area;
   }

   public static Boolean IsCounterClockWise(Array<Vector2> vect) {
      // We just return true for lines
      if (vect.size < 3)
         return true;
      return (GetSignedArea(vect) > 0.0f);
   }

   public static Boolean IsCounterClockWise(Vector2[] vect) {
      // We just return true for lines
      if (vect.length < 3)
         return true;
      return (GetSignedArea(vect) > 0.0f);
   }

   // / <summary>
   // / Decompose the polygon into several smaller non-concave polygon.
   // / If the polygon is already convex, it will return the original polygon,
   // unless it is over Settings.MaxPolygonVertices.
   // / Precondition: Counter Clockwise polygon
   // / </summary>
   // / <param name="vertices"></param>
   // / <returns></returns>
   public static Array<Array<Vector2>> ConvexPartition(Array<Vector2> vertices) {
      // We force it to CCW as it is a precondition in this algorithm.
      // vertices.ForceCounterClockWise();
      if (!IsCounterClockWise(vertices)) {
         // Collections.reverse(vertices);
         vertices.reverse();
         // Array<Vector2> reversed = new Array<Vector2>(vertices.size);
         // for (int i = vertices.size - 1; i <= 0; i--) {
         // reversed.add(vertices.get(i));
         // }
         // vertices = reversed;
      }
      Array<Array<Vector2>> list = new Array<Array<Vector2>>();
      float d, lowerDist, upperDist;
      Vector2 p;
      Vector2 lowerInt = new Vector2();
      Vector2 upperInt = new Vector2(); // intersection points
      int lowerIndex = 0, upperIndex = 0;
      Array<Vector2> lowerPoly, upperPoly;
      for (int i = 0; i < vertices.size; ++i) {
         if (Reflex(i, vertices)) {
            lowerDist = upperDist = Float.MAX_VALUE; // std::numeric_limits<qreal>::max();
            for (int j = 0; j < vertices.size; ++j) {
               // if line intersects with an edge
               if (Left(At(i - 1, vertices), At(i, vertices),
                     At(j, vertices))
                     && RightOn(At(i - 1, vertices), At(i, vertices),
                           At(j - 1, vertices))) {
                  // find the point of intersection
                  p = LineIntersect(At(i - 1, vertices), At(i, vertices),
                        At(j, vertices), At(j - 1, vertices));
                  if (Right(At(i + 1, vertices), At(i, vertices), p)) {
                     // make sure it's inside the poly
                     d = SquareDist(At(i, vertices), p);
                     if (d < lowerDist) {
                        // keep only the closest intersection
                        lowerDist = d;
                        lowerInt = p;
                        lowerIndex = j;
                     }
                  }
               }
               if (Left(At(i + 1, vertices), At(i, vertices),
                     At(j + 1, vertices))
                     && RightOn(At(i + 1, vertices), At(i, vertices),
                           At(j, vertices))) {
                  p = LineIntersect(At(i + 1, vertices), At(i, vertices),
                        At(j, vertices), At(j + 1, vertices));
                  if (Left(At(i - 1, vertices), At(i, vertices), p)) {
                     d = SquareDist(At(i, vertices), p);
                     if (d < upperDist) {
                        upperDist = d;
                        upperIndex = j;
                        upperInt = p;
                     }
                  }
               }
            }
            // if there are no vertices to connect to, choose a point in the
            // middle
            if (lowerIndex == (upperIndex + 1) % vertices.size) {
               Vector2 sp = new Vector2((lowerInt.x + upperInt.x) / 2,
                     (lowerInt.y + upperInt.y) / 2);
               lowerPoly = Copy(i, upperIndex, vertices);
               lowerPoly.add(sp);
               upperPoly = Copy(lowerIndex, i, vertices);
               upperPoly.add(sp);
            } else {
               double highestScore = 0, bestIndex = lowerIndex;
               while (upperIndex < lowerIndex)
                  upperIndex += vertices.size;
               for (int j = lowerIndex; j <= upperIndex; ++j) {
                  if (CanSee(i, j, vertices)) {
                     double score = 1 / (SquareDist(At(i, vertices),
                           At(j, vertices)) + 1);
                     if (Reflex(j, vertices)) {
                        if (RightOn(At(j - 1, vertices),
                              At(j, vertices), At(i, vertices))
                              && LeftOn(At(j + 1, vertices),
                                    At(j, vertices),
                                    At(i, vertices))) {
                           score += 3;
                        } else {
                           score += 2;
                        }
                     } else {
                        score += 1;
                     }
                     if (score > highestScore) {
                        bestIndex = j;
                        highestScore = score;
                     }
                  }
               }
               lowerPoly = Copy(i, (int) bestIndex, vertices);
               upperPoly = Copy((int) bestIndex, i, vertices);
            }
            list.addAll(ConvexPartition(lowerPoly));
            list.addAll(ConvexPartition(upperPoly));
            return list;
         }
      }
      // polygon is already convex
      if (vertices.size > MaxPolygonVertices) {
         lowerPoly = Copy(0, vertices.size / 2, vertices);
         upperPoly = Copy(vertices.size / 2, 0, vertices);
         list.addAll(ConvexPartition(lowerPoly));
         list.addAll(ConvexPartition(upperPoly));
      } else
         list.add(vertices);
      // The polygons are not guaranteed to be with collinear points. We
      // remove
      // them to be sure.
      for (int i = 0; i < list.size; i++) {
         list.set(i, SimplifyTools.CollinearSimplify(list.get(i), 0));
      }
      // Remove empty vertice collections
      for (int i = list.size - 1; i >= 0; i--) {
         if (list.get(i).size == 0)
            list.removeIndex(i);
      }
      return list;
   }

   private static Boolean CanSee(int i, int j, Array<Vector2> vertices) {
      if (Reflex(i, vertices)) {
         if (LeftOn(At(i, vertices), At(i - 1, vertices), At(j, vertices))
               && RightOn(At(i, vertices), At(i + 1, vertices),
                     At(j, vertices)))
            return false;
      } else {
         if (RightOn(At(i, vertices), At(i + 1, vertices), At(j, vertices))
               || LeftOn(At(i, vertices), At(i - 1, vertices),
                     At(j, vertices)))
            return false;
      }
      if (Reflex(j, vertices)) {
         if (LeftOn(At(j, vertices), At(j - 1, vertices), At(i, vertices))
               && RightOn(At(j, vertices), At(j + 1, vertices),
                     At(i, vertices)))
            return false;
      } else {
         if (RightOn(At(j, vertices), At(j + 1, vertices), At(i, vertices))
               || LeftOn(At(j, vertices), At(j - 1, vertices),
                     At(i, vertices)))
            return false;
      }
      for (int k = 0; k < vertices.size; ++k) {
         if ((k + 1) % vertices.size == i || k == i
               || (k + 1) % vertices.size == j || k == j) {
            continue; // ignore incident edges
         }
         Vector2 intersectionPoint = new Vector2();
         if (LineIntersect(At(i, vertices), At(j, vertices),
               At(k, vertices), At(k + 1, vertices), true, true,
               intersectionPoint)) {
            return false;
         }
      }
      return true;
   }

   public static Vector2 LineIntersect(Vector2 p1, Vector2 p2, Vector2 q1,
         Vector2 q2) {
      Vector2 i = new Vector2();
      float a1 = p2.y - p1.y;
      float b1 = p1.x - p2.x;
      float c1 = a1 * p1.x + b1 * p1.y;
      float a2 = q2.y - q1.y;
      float b2 = q1.x - q2.x;
      float c2 = a2 * q1.x + b2 * q1.y;
      float det = a1 * b2 - a2 * b1;
      if (!FloatEquals(det, 0)) {
         // lines are not parallel
         i.x = (b2 * c1 - b1 * c2) / det;
         i.y = (a1 * c2 - a2 * c1) / det;
      }
      return i;
   }

   public static Boolean FloatEquals(float value1, float value2) {
      return Math.abs(value1 - value2) <= Epsilon;
   }

   // / <summary>
   // / This method detects if two line segments (or lines) intersect,
   // / and, if so, the point of intersection. Use the
   // <paramname="firstIsSegment"/> and
   // / <paramname="secondIsSegment"/> parameters to set whether the
   // intersection point
   // / must be on the first and second line segments. Setting these
   // / both to true means you are doing a line-segment to line-segment
   // / intersection. Setting one of them to true means you are doing a
   // / line to line-segment intersection test, and so on.
   // / Note: If two line segments are coincident, then
   // / no intersection is detected (there are actually
   // / infinite intersection points).
   // / Author: Jeremy Bell
   // / </summary>
   // / <param name="point1">The first point of the first line segment.</param>
   // / <param name="point2">The second point of the first line
   // segment.</param>
   // / <param name="point3">The first point of the second line
   // segment.</param>
   // / <param name="point4">The second point of the second line
   // segment.</param>
   // / <param name="point">This is set to the intersection
   // / point if an intersection is detected.</param>
   // / <param name="firstIsSegment">Set this to true to require that the
   // / intersection point be on the first line segment.</param>
   // / <param name="secondIsSegment">Set this to true to require that the
   // / intersection point be on the second line segment.</param>
   // / <returns>True if an intersection is detected, false
   // otherwise.</returns>
   public static Boolean LineIntersect(Vector2 point1, Vector2 point2,
         Vector2 point3, Vector2 point4, Boolean firstIsSegment,
         Boolean secondIsSegment, Vector2 point) {
      point = new Vector2();
      // these are reused later.
      // each lettered sub-calculation is used twice, except
      // for b and d, which are used 3 times
      float a = point4.y - point3.y;
      float b = point2.x - point1.x;
      float c = point4.x - point3.x;
      float d = point2.y - point1.y;
      // denominator to solution of linear system
      float denom = (a * b) - (c * d);
      // if denominator is 0, then lines are parallel
      if (!(denom >= -Epsilon && denom <= Epsilon)) {
         float e = point1.y - point3.y;
         float f = point1.x - point3.x;
         float oneOverDenom = 1.0f / denom;
         // numerator of first equation
         float ua = (c * e) - (a * f);
         ua *= oneOverDenom;
         // check if intersection point of the two lines is on line segment 1
         if (!firstIsSegment || ua >= 0.0f && ua <= 1.0f) {
            // numerator of second equation
            float ub = (b * e) - (d * f);
            ub *= oneOverDenom;
            // check if intersection point of the two lines is on line
            // segment 2
            // means the line segments intersect, since we know it is on
            // segment 1 as well.
            if (!secondIsSegment || ub >= 0.0f && ub <= 1.0f) {
               // check if they are coincident (no collision in this case)
               if (ua != 0f || ub != 0f) {
                  // There is an intersection
                  point.x = point1.x + ua * b;
                  point.y = point1.y + ua * d;
                  return true;
               }
            }
         }
      }
      return false;
   }

   // precondition: ccw
   private static Boolean Reflex(int i, Array<Vector2> vertices) {
      return Right(i, vertices);
   }

   private static Boolean Right(int i, Array<Vector2> vertices) {
      return Right(At(i - 1, vertices), At(i, vertices), At(i + 1, vertices));
   }

   private static Boolean Left(Vector2 a, Vector2 b, Vector2 c) {
      return Area(a, b, c) > 0;
   }

   private static Boolean LeftOn(Vector2 a, Vector2 b, Vector2 c) {
      return Area(a, b, c) >= 0;
   }

   private static Boolean Right(Vector2 a, Vector2 b, Vector2 c) {
      return Area(a, b, c) < 0;
   }

   private static Boolean RightOn(Vector2 a, Vector2 b, Vector2 c) {
      return Area(a, b, c) <= 0;
   }

   public static float Area(Vector2 a, Vector2 b, Vector2 c) {
      return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y);
   }

   private static float SquareDist(Vector2 a, Vector2 b) {
      float dx = b.x - a.x;
      float dy = b.y - a.y;
      return dx * dx + dy * dy;
   }
}

