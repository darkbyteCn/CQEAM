package com.sino.sinoflow.constant;

public interface ButtonMask {
    final int CONFIRMMESSAGE_MASK = 0x8000;
    final int FINISHMESSAGE_MASK = 0x4000;
    final int REVIEW_STATUS_MASK = 0x200;
    final int REVIEW_MASK = 0x100;
    final int CANCEL_MASK = 0x1;
    final int SPECIALSEND_MASK = 0x2;
    final int SENDBACK_MASK = 0x4;
    final int VIEWPROCESS_MASK = 0x8;
    final int SENDTO_MASK = 0x10;
    final int ASK_MASK = 0x80;
    final int SIGN_MASK = 0x400;
    final int SAVE_MASK = 0x800;
    final int COMPLETE_MASK = 0x1000;
    final int CYCLE_MASK = 0x20;
    final int CYCLE_STATUS_MASK = 0x40;
}

