/*
 * $Header: /cvs/jaxen/jaxen/src/java/main/org/jaxen/expr/VisitorSupport.java,v 1.4 2006/02/05 21:47:40 elharo Exp $
 * $Revision: 1.4 $
 * $Date: 2006/02/05 21:47:40 $
 *
 * ====================================================================
 *
 * Copyright 2000-2002 bob mcwhirter & James Strachan.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 * 
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 * 
 *   * Neither the name of the Jaxen Project nor the names of its
 *     contributors may be used to endorse or promote products derived 
 *     from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 * This software consists of voluntary contributions made by many 
 * individuals on behalf of the Jaxen Project and was originally 
 * created by bob mcwhirter <bob@werken.com> and 
 * James Strachan <jstrachan@apache.org>.  For more information on the 
 * Jaxen Project, please see <http://www.jaxen.org/>.
 * 
 * $Id: VisitorSupport.java,v 1.4 2006/02/05 21:47:40 elharo Exp $
 */
package org.navimatrix.jaxen.expr;
public class VisitorSupport implements Visitor {
    public void visit(PathExpr path) {
    }
    public void visit(LocationPath path) {
    }
    public void visit(LogicalExpr expr) {
    }
    public void visit(EqualityExpr expr) {
    }
    public void visit(FilterExpr expr) {
    }
    public void visit(RelationalExpr expr) {
    }
    public void visit(AdditiveExpr expr) {
    }
    public void visit(MultiplicativeExpr expr) {
    }
    public void visit(UnaryExpr expr) {
    }
    public void visit(UnionExpr expr) {
    }
    public void visit(NumberExpr expr) {
    }
    public void visit(LiteralExpr expr) {
    }
    public void visit(VariableReferenceExpr expr) {
    }
    public void visit(FunctionCallExpr expr) {
    }
    public void visit(NameStep step){
    }
    public void visit(ProcessingInstructionNodeStep step){
    }
    public void visit(AllNodeStep step){
    }
    public void visit(TextNodeStep step){
    }
    public void visit(CommentNodeStep step){
    }
    public void visit(Predicate predicate){
    }
}
