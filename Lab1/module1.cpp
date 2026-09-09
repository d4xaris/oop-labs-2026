#include "module1.h"
#include "resource1.h"

extern HINSTANCE hInst;

static INT_PTR CALLBACK DlgProc1(HWND hDlg, UINT msg, WPARAM wParam, LPARAM lParam) {
    switch (msg) {
    case WM_INITDIALOG:
        return (INT_PTR)TRUE;
    case WM_COMMAND:
        switch (LOWORD(wParam)) {
        case IDC_NEXT:
            EndDialog(hDlg, 1);
            return (INT_PTR)TRUE;
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

int Func_MOD1(HWND hWnd) {
    return (int)DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOG1), hWnd, DlgProc1);
}