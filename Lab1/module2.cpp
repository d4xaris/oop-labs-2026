#define _CRT_SECURE_NO_WARNINGS
#include "module2.h"
#include "resource2.h"
#include <string.h>

extern HINSTANCE hInst;
static char s_buf[256];

static INT_PTR CALLBACK DlgProc2(HWND hDlg, UINT msg, WPARAM wParam, LPARAM lParam) {
    switch (msg) {
    case WM_INITDIALOG:
        SendDlgItemMessage(hDlg, IDC_LIST1, LB_ADDSTRING, 0, (LPARAM)"Група ПЗ-21");
        SendDlgItemMessage(hDlg, IDC_LIST1, LB_ADDSTRING, 0, (LPARAM)"Група ПЗ-22");
        SendDlgItemMessage(hDlg, IDC_LIST1, LB_ADDSTRING, 0, (LPARAM)"Група КН-21");
        SendDlgItemMessage(hDlg, IDC_LIST1, LB_ADDSTRING, 0, (LPARAM)"Група КН-22");
        return (INT_PTR)TRUE;
    case WM_COMMAND:
        switch (LOWORD(wParam)) {
        case IDOK: {
            int idx = (int)SendDlgItemMessage(hDlg, IDC_LIST1, LB_GETCURSEL, 0, 0);
            if (idx != LB_ERR)
                SendDlgItemMessage(hDlg, IDC_LIST1, LB_GETTEXT, idx, (LPARAM)s_buf);
            else
                s_buf[0] = '\0';
            EndDialog(hDlg, 1);
            return (INT_PTR)TRUE;
        }
        case IDCANCEL:
            EndDialog(hDlg, 0);
            return (INT_PTR)TRUE;
        }
        break;
    case WM_CLOSE:
        EndDialog(hDlg, 0);
        return (INT_PTR)TRUE;
    }
    return (INT_PTR)FALSE;
}

int Func_MOD2(HWND hWnd, char* outText, int outSize) {
    int result = (int)DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOG2), hWnd, DlgProc2);
    if (result != 0) {
        strncpy(outText, s_buf, outSize);
        outText[outSize - 1] = '\0';
    }
    return result;
}