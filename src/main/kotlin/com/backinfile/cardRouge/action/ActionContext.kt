package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.dungeon.Dungeon
import com.backinfile.cardRouge.human.HumanBase

class ActionContext(val dungeon: Dungeon, val board: Board, val human: HumanBase, val hostCard: Card)