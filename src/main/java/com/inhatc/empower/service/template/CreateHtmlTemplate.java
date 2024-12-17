package com.inhatc.empower.service.template;

public class CreateHtmlTemplate {
    public static String createHtmlTemplate(String name, String status) {
        return """
            <div style="font-family: Arial, sans-serif;">
                <h2 style="color: #333;">휴가 상세내역</h2>
                <table style="border-collapse: collapse; width: 100%; margin-top: 20px;">
                    <tr>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd; width: 150px;">사원 ID</td>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">202445081</td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">이름</td>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">%s</td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">휴가 정보</td>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">연차</td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">승인 상태</td>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">%s</td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">시작일</td>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">2024-11-18</td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">종료일</td>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">2024-11-19</td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">사유</td>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">휴가 싶어서</td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">신청일시</td>
                        <td style="padding: 10px; border-bottom: 1px solid #ddd;">2024-11-18 14:48:10</td>
                    </tr>
                </table>
            </div>
            """.formatted(name, status);
    }
}
